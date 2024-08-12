package org.repylot.controller.task;

import org.repylot.controller.conditions.Condition;
import org.repylot.controller.datalake.DataLakeWriter;
import org.repylot.controller.scrapper.Crawler;
import org.repylot.controller.scrapper.Retriever;
import org.repylot.controller.scrapper.Scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class PythonScrappingTask extends TimerTask {
    private final Crawler crawler;
    private final Scrapper scrapper;
    private final DataLakeWriter writer;
    private final Retriever retriever;

    private final Condition condition;

    private final String url;
    private int page;

    public PythonScrappingTask(Crawler crawler, Scrapper scrapper, DataLakeWriter writer, Retriever retriever, Condition condition) {
        this.crawler = crawler;
        this.scrapper = scrapper;
        this.writer = writer;
        this.retriever = retriever;
        this.condition = condition;
        this.url = "https://github.com/search?q=%23python&type=repositories&p=";
        this.page = 1;
    }

    @Override
    public void run() {
        ArrayList<String> subUrls = getSubUrls();
        for (int index = 0; index < subUrls.size(); index++) {
            ArrayList<String> documents = getExtract(subUrls, index);

            for (String document : documents) {
                if (condition.satisfy(document)) {
                    try {
                        System.out.println("Saving " + document);
                        writer.save(document, retriever.retrieve(document));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }

        page++;
    }

    private ArrayList<String> getExtract(ArrayList<String> subUrls, int index) {
        try {
            return scrapper.extract(subUrls.get(index) + "/tree/master/");
        } catch (Exception  e) {
            return new ArrayList<>();
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
