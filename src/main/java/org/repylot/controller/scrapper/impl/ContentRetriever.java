package org.repylot.controller.scrapper.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ContentRetriever  {
    private final String rawClass = "types__StyledButton-sc-ws60qy-0";

    public String retrieve(String url) throws IOException {
        Document doc = getDocument(url);
        String href = rawContentLink(doc);
        return rawContent(href);
    }

    private static String rawContent(String href) throws IOException {
        return getDocument(href).body().toString();
    }

    private static Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private String rawContentLink(Document doc) {
        Elements link = doc.select("a." + rawClass);
        return link.get(2).attr("href");
    }
}
