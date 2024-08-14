package org.repylot;

import org.repylot.controller.Controller;
import org.repylot.controller.scrapper.impl.RepoJSONCrawler;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        new Controller().run();
    }
}