package org.repylot.controller.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepoScrapper implements Scrapper {
    private final String divClass = "react-directory-filename-column";
    private final String aClass = "Link--primary";
    private final String codeClass = "react-code-lines";

    @Override
    public ArrayList<String> extract(String url) throws IOException, InterruptedException {
        System.out.println(url);

        Elements divs = getDivElements(url);
        ArrayList<String> urls = new ArrayList<>();

        for (Element div : divs) {
            Element link = div.selectFirst("a." + aClass);

            String href = "https://github.com" + link.attr("href");
            ArrayList<String> links = getSubDivElements(href);
        }

        return null;
    }

    private ArrayList<String> getSubDivElements(String href) throws IOException {
        Document doc = Jsoup.connect(href).get();
        ArrayList<String> links = getDynamicElements(href, doc);

        Elements divs = doc.select("div." + divClass);
        for (Element div : divs) {
            Element link = div.selectFirst("a." + aClass);
            links.add("https://github.com" + link.attr("href"));
        }

        return links;
    }

    private static ArrayList<String> getDynamicElements(String href, Document doc) {
        String docString = doc.toString();

        Pattern pattern = Pattern.compile("\\[\\{\"name\":\"([\\w\\.\\s]+)\",\"path\":\"([^\\s\\\"]+)\"");
        Matcher matcher = pattern.matcher(docString);

        ArrayList<String> links = new ArrayList<>();
        while (matcher.find())
            links.add(href + matcher.group(2));
        return links;
    }

    private Elements getDivElements(String url) throws IOException, InterruptedException {
        Document doc = Jsoup.connect(url).get();
        Elements divs = doc.select("div." + divClass);
        return divs;
    }
}
