package org.repylot.controller.datalake;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileDataLakeWriter implements DataLakeWriter {
    private final String dataLakePath = "datalake";

    @Override
    public void save(String name, String content) throws IOException {
        File file = new File(filePath(name));
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(content);
        bufferedWriter.flush();
    }

    private String filePath(String name) {
        return dataLakePath + "/" + name.replace("/", "_").replace(":", "");
    }


}
