package org.githubscrapper;

import org.githubscrapper.controller.ScrappingTask;
import org.githubscrapper.controller.datalake.DataLakeWriter;
import org.githubscrapper.controller.datalake.FileWriter;
import org.githubscrapper.controller.scrapper.Crawler;
import org.githubscrapper.controller.scrapper.Scrapper;
import org.githubscrapper.controller.conditions.PythonCondition;
import org.githubscrapper.controller.scrapper.GithubCrawler;
import org.githubscrapper.controller.scrapper.GithubScrapper;

import java.util.Timer;

public class Controller {
    private final Crawler githubCrawler;
    private final Scrapper githubScrapper;
    private final DataLakeWriter fileWriter;

    public Controller() {
        this.githubCrawler = new GithubCrawler(new PythonCondition());
        this.githubScrapper = new GithubScrapper();
        this.fileWriter = new FileWriter();
    }

    public void run() {
        Timer timer = new Timer();
        timer.schedule(new ScrappingTask(githubCrawler,
                githubScrapper,
                fileWriter), 2000);
    }
}
