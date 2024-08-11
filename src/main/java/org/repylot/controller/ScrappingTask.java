package org.repylot.controller;

import org.repylot.controller.datalake.DataLakeWriter;
import org.repylot.controller.scrapper.Crawler;
import org.repylot.controller.scrapper.Scrapper;

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
        ArrayList<String> subUrls = crawler.getSubUrls(url + page);
        for (int index = 0; index < subUrls.size(); index++) {
            String document = scrapper.extract(subUrls.get(index));
            writer.save(subUrls.get(index) + index, document);
        }
        page++;
    }
}
