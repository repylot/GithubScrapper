package org.repylot.controller.scrapper;

import java.io.IOException;

public interface Retriever {
    String retrieve(String url) throws IOException;
}
