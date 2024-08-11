package org.repylot.controller.scrapper;

import org.repylot.controller.conditions.Condition;

import java.util.ArrayList;

public class GithubCrawler implements Crawler {
    private final Condition condition;

    public GithubCrawler(Condition condition) {
        this.condition = condition;
    }

    @Override
    public ArrayList<String> getSubUrls(String url) {
        return null;
    }
}
