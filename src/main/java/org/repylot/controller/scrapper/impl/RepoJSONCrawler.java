package org.repylot.controller.scrapper.impl;

import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.repylot.controller.scrapper.Crawler;

import java.io.IOException;
import java.util.ArrayList;

public class RepoJSONCrawler implements Crawler {
    @Override
    public ArrayList<String> getSubUrls(String url) throws IOException {
        Document doc = getDocument(url);
        String repoJSON = doc.body().text();

        JsonObject jsonObject = JsonParser.parseString(repoJSON).getAsJsonObject();
        JsonObject payloads = jsonObject.getAsJsonObject("payload");
        JsonArray repositories = payloads.getAsJsonArray("results");

        ArrayList<String> links = new ArrayList<>();

        repositories.forEach(repo -> {
                links.add(
                    "https://github.com/" + repo
                        .getAsJsonObject()
                        .get("hl_name")
                        .getAsString()
                );
        });

        return links;
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}

