package org.githubscrapper.controller.scrapper;

import org.githubscrapper.controller.conditions.Condition;

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
