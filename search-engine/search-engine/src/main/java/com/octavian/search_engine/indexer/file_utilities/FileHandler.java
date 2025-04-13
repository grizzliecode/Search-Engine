package com.octavian.search_engine.indexer.file_utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FileHandler {
    public static final String[] supportedExtension = {"pdf", "docs", "docx", "exe"};
    public static String getExtension(String path) {
        return com.google.common.io.Files.getFileExtension(path);
    }

    public static boolean isText(Path path, Logger logger) {
        String type = null;
        try {
            type = Files.probeContentType(path);
        } catch (IOException e) {
            logger.warning("Couldn't find the type of file " + path.getFileName());
        }
        if (type != null && type.startsWith("text")) {
            return true;
        }
        return false;
    }

    public static boolean isInside(List<String> ignored, String path) {
        return ignored.contains(path);
    }

    public static float getPathEntropy(Path path){
        float entropy = 0f;
            String input = path.toString().toLowerCase();
            if(input.isEmpty()){
                entropy = 0.0f;
            }
            else{
                Map<Character, Integer> histogram = new HashMap<>();
                for(char c: input.toCharArray()){
                    histogram.put(c, histogram.getOrDefault(c,0)+1);
                }
                int length = input.length();
                for(int count: histogram.values()){
                    float prob = (float) count/length;
                    entropy -= (float) (prob*(Math.log(prob)/Math.log(2)));
                }
        }
        return entropy;
    }

    public static boolean isExtensionSupported(String extension){
        boolean ok = false;
        for(String ex: FileHandler.supportedExtension ){
            if(ex.equals(extension)){
                ok = true;
                break;
            }
        }
        return ok;
    }
}
