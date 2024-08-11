package org.repylot.controller.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepoCrawler implements Crawler {
    private final String divClass = "search-title";
    private final String aClass = "Link__StyledLink-sc-14289xe-0";

    @Override
    public ArrayList<String> getSubUrls(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements divs = doc.select("div." + divClass);

        return (ArrayList<String>) divs.stream().map(div -> div.selectFirst("a." + aClass))
                .map(link -> link.attr("href"))
                .map(href -> "https://github.com" + href)
                .collect(Collectors.toList());
    }
}
