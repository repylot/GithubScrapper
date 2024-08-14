package org.repylot.controller;

import org.repylot.controller.conditions.Condition;
import org.repylot.controller.conditions.JavaCondition;
import org.repylot.controller.conditions.JavaScriptCondition;
import org.repylot.controller.datalake.FileDataLakeWriter;
import org.repylot.controller.scrapper.impl.ContentRetriever;
import org.repylot.controller.scrapper.impl.RepoJSONCrawler;
import org.repylot.controller.scrapper.impl.RepoScrapper;
import org.repylot.controller.task.JavaScrappingTask;
import org.repylot.controller.task.MultipleScrappingTask;
import org.repylot.controller.task.PythonScrappingTask;

import java.util.Timer;

public class Controller {

    public void run() {
        Timer timer = new Timer();
        timer.schedule(new MultipleScrappingTask(new RepoJSONCrawler(),
                new RepoScrapper(),
                new FileDataLakeWriter(),
                new ContentRetriever(),
                object -> new JavaCondition().satisfy(object) || new JavaScriptCondition().satisfy(object),
                "https://github.com/search?q=%23java&type=repositories%3Fp%3D2&p="
        ), 0, 2000);
    }
}
