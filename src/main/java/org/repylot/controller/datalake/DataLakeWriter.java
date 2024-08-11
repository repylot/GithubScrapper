package org.repylot.controller.datalake;

import java.io.IOException;

public interface DataLakeWriter {
    void save(String name, String content) throws IOException;
}
