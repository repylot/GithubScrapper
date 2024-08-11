package org.repylot.controller.scrapper;

import java.io.IOException;
import java.util.ArrayList;

public interface Crawler {
    ArrayList<String> getSubUrls(String url) throws IOException;
}
