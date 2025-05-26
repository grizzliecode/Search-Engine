package com.octavian.search_engine.search.widget;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExecutableProcessor {
    public static void showExeHeaderWindow(String exeFilePath) throws Exception {
        File exeFile = new File(exeFilePath);
        if (!exeFile.exists() || !exeFile.isFile()) {
            throw new IllegalArgumentException("Invalid file path: " + exeFilePath);
        }
        Tika tika = new Tika();
        Metadata metadata = new Metadata();
        try (FileInputStream input = new FileInputStream(exeFile)) {
            tika.parse(input, metadata);
        }
        StringBuilder metaText = new StringBuilder();
        metaText.append("Metadata for: ").append(exeFile.getName()).append("\n\n");
        for (String name : metadata.names()) {
            metaText.append(name).append(": ").append(metadata.get(name)).append("\n");
        }
        String metadataString = metaText.toString().replace("\"", "\\\"").replace("\n", " ");
        String[] cmd = new String[] {
                "py", "printer.py", metadataString
        };

        try {
            Process process = new ProcessBuilder(cmd)
                    .redirectErrorStream(true)
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to launch Python printer script");
        }
    }
}
