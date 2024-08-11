package org.repylot.controller.datalake;

public interface DataLakeWriter {
    void save(String name, String content);
}
