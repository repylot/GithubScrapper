package org.repylot.controller;

import org.repylot.controller.conditions.PythonCondition;
import org.repylot.controller.datalake.FileDataLakeWriter;
import org.repylot.controller.scrapper.impl.ContentRetriever;
import org.repylot.controller.scrapper.impl.RepoCrawler;
import org.repylot.controller.scrapper.impl.RepoScrapper;
import org.repylot.controller.task.PythonScrappingTask;

import java.util.Timer;

public class Controller {

    public void run() {
        Timer timer = new Timer();
        timer.schedule(new PythonScrappingTask(new RepoCrawler(),
                new RepoScrapper(),
                new FileDataLakeWriter(),
                new ContentRetriever(),
                new PythonCondition()), 0, 2000);
    }
}
