package org.githubscrapper.controller.datalake;

public interface DataLakeWriter {
    void save(String name, String content);
}
