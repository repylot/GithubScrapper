package org.repylot.controller.scrapper.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.repylot.controller.scrapper.Retriever;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentRetriever implements Retriever {

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
        Pattern pattern = Pattern.compile("\\\"rawBlobUrl\\\"\\:\\\"([\\w\\.\\s:/-]+)\\\"");
        Matcher matcher = pattern.matcher(doc.toString());

        matcher.find();
        return matcher.group(1);
    }
}
