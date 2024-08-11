package org.repylot.controller;

import org.repylot.controller.datalake.DataLakeWriter;
import org.repylot.controller.scrapper.Crawler;
import org.repylot.controller.scrapper.Scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class ScrappingTask extends TimerTask {
    private final Crawler crawler;
    private final Scrapper scrapper;
    private final DataLakeWriter writer;

    private final String url;
    private int page;

    public ScrappingTask(Crawler crawler, Scrapper scrapper, DataLakeWriter writer) {
        this.crawler = crawler;
        this.scrapper = scrapper;
        this.writer = writer;

        this.url = "https://github.com/search?q=%23python&type=repositories&p=";
        this.page = 1;
    }

    @Override
    public void run() {
        ArrayList<String> subUrls = getSubUrls();
        System.out.println(subUrls);

        for (int index = 0; index < subUrls.size(); index++) {
            ArrayList<String> documents = getExtract(subUrls, index);
            System.out.println(documents);
        }

        page++;
    }

    private ArrayList<String> getExtract(ArrayList<String> subUrls, int index) {
        try {
            return scrapper.extract(subUrls.get(index) + "/tree/master/");
        } catch (Exception  e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getSubUrls() {
        try {
            return crawler.getSubUrls(url + page);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
