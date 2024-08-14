package org.repylot.controller.scrapper.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.repylot.controller.scrapper.Scrapper;
import org.repylot.controller.scrapper.exceptions.ElementNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepoScrapper implements Scrapper {
    private final String divClass = "react-directory-filename-column";
    private final String aClass = "Link--primary";

    private ArrayList<String> result = new ArrayList<>();

    private String mainUrl;
    private int recursiveIteration = 0;

    @Override
    public ArrayList<String> extract(String url) throws IOException {
        if (recursiveIteration == 0) {
            mainUrl = url;
            result = new ArrayList<>();
        }

        ArrayList<String> links = getSubUrls(url, 0);
        if (recursiveIteration == 0) links.remove(0);

        for (String link : links) {
            if (url.length() < link.length() && !result.contains(link)) {
                System.out.println(link);
                result.add(link);
                recursiveIteration++;
                extract(link);
            }
        }

        if (recursiveIteration == 0) {
            return result;

        }else {
            recursiveIteration--;
            return new ArrayList<>();
        }
    }

    private ArrayList<String> getSubUrls(String href, int recursiveIteration) throws IOException {
        ArrayList<String> divUrls = getDivUrls(href);

        try {
            HashSet<String> urlSet = new HashSet<>(divUrls);
            if (urlSet.size() >= 2 || recursiveIteration > 4) {
                return divUrls;
            }
            throw new ElementNotFoundException();

        } catch (ElementNotFoundException e) {
            if (recursiveIteration < 4) return getSubUrls(href, ++recursiveIteration);
            return new ArrayList<>();
        }
    }

    private ArrayList<String> getDivUrls(String href) {
        if (href.equals("https://github.com/CyC2018/CS-Notes/tree/master/docs/_style/components"))
            System.out.println("ok");
        Document doc = getDocument(href);
        if (doc == null) return new ArrayList<>();

        ArrayList<String> links = getDynamicElements(doc);

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

    private ArrayList<String> getDynamicElements(Document doc) {
        String docString = doc.toString();

        Pattern pattern = Pattern.compile("\\[\\{\"name\":\"([\\w\\.\\s]+)\",\"path\":\"([^\\s\\\"]+)\"");
        Matcher matcher = pattern.matcher(docString);

        ArrayList<String> links = new ArrayList<>();
        while (matcher.find())
            links.add(this.mainUrl + matcher.group(2));

        return links;
    }
}
