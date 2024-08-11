package org.repylot;

import org.repylot.controller.ScrappingTask;
import org.repylot.controller.conditions.PythonCondition;
import org.repylot.controller.datalake.DataLakeWriter;
import org.repylot.controller.datalake.FileWriter;
import org.repylot.controller.scrapper.Crawler;
import org.repylot.controller.scrapper.Scrapper;
import org.repylot.controller.scrapper.RepoCrawler;
import org.repylot.controller.scrapper.RepoScrapper;

import java.util.Timer;

public class Controller {
    private final Crawler githubCrawler;
    private final Scrapper githubScrapper;
    private final DataLakeWriter fileWriter;

    public Controller() {
        this.githubCrawler = new RepoCrawler();
        this.githubScrapper = new RepoScrapper();
        this.fileWriter = new FileWriter();
    }

    public void run() {
        Timer timer = new Timer();
        timer.schedule(new ScrappingTask(githubCrawler,
                githubScrapper,
                fileWriter), 0, 2000);
    }
}
