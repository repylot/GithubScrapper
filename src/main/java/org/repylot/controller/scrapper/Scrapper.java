package org.repylot.controller.scrapper;

import java.io.IOException;
import java.util.ArrayList;

public interface Scrapper {
    ArrayList<String> extract(String url) throws IOException, InterruptedException;
}
