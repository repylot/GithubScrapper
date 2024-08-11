package org.repylot.controller.scrapper.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.repylot.controller.scrapper.Scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepoScrapper implements Scrapper {
    private final String divClass = "react-directory-filename-column";
    private final String aClass = "Link--primary";
    private final String codeClass = "react-code-lines";

    private String mainUrl;
    private int recursiveIteration = 0;

    @Override
    public ArrayList<String> extract(String url) throws IOException, InterruptedException {
        if (recursiveIteration == 0) mainUrl = url;
        ArrayList<String> links = getDivUrls(url);
        if (recursiveIteration == 0) links.remove(0);

        ArrayList<String> result = new ArrayList<>();
        if (links.size() <= 1) return links;

        for (String link : links) {
            System.out.println(link);
            if (url.length() < link.length()) {
                result.add(link);
                recursiveIteration++;
                result.addAll(extract(link));
            }
        }

        recursiveIteration--;
        return result;
    }

    private ArrayList<String> getDivUrls(String href) throws IOException {
        Document doc = getDocument(href);
        if (doc == null) return new ArrayList<String>();

        ArrayList<String> links = getDynamicElements(href, doc);

        int i = 0;
        Elements divs = doc.select("div." + divClass);
        for (Element div : divs) {
            if (i++ % 2 == 0) continue;
            Element link = div.selectFirst("a." + aClass);
            links.add("https://github.com" + link.attr("href"));
        }

        return links;
    }

    private Document getDocument(String href) {
        try {
            return Jsoup.connect(href).get();
        } catch (IOException e) {
            return null;
        }
    }

    private ArrayList<String> getDynamicElements(String href, Document doc) {
        String docString = doc.toString();

        Pattern pattern = Pattern.compile("\\[\\{\"name\":\"([\\w\\.\\s]+)\",\"path\":\"([^\\s\\\"]+)\"");
        Matcher matcher = pattern.matcher(docString);

        ArrayList<String> links = new ArrayList<>();
        while (matcher.find())
            links.add(this.mainUrl + matcher.group(2));

        return links;
    }
}
