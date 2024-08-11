package org.repylot.controller.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ContentRetriever  {
    private final String rawClass = "types__StyledButton-sc-ws60qy-0";

    public String retrieve(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements link = doc.select("a." + rawClass);
        String href = link.get(2).attr("href");

        return Jsoup.connect(href).get().body().toString();
    }
}
