package org.githubscrapper.controller.scrapper;

import java.util.ArrayList;

public interface Crawler {
    ArrayList<String> getSubUrls(String url);
}
