package com.octavian.search_engine.search.widget;

import io.swagger.v3.oas.models.security.SecurityScheme;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class TextSummarizer {
    public static String summarizeText(String inputText) {
        Map<String, Integer> f = new HashMap<>();
        List<String> a = Arrays.stream(inputText.split("[,.:;\\s]+")).collect(Collectors.toList());
        for(String word: a){
            if(f.get(word) != null){
                int freq = f.get(word);
                f.remove(word);
                f.put(word, freq + 1);
            }
            else{
                f.put(word,1);
            }
        }
        List<Map.Entry<String, Integer>> top10 = f.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .toList();

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : top10) {
            result.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return result.toString();
    }

    public static void showSummaryWindow(String content) {
        String summary = TextSummarizer.summarizeText(content);
        System.out.println(summary);
        String[] cmd = new String[] {
                "py", "printer.py", summary
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
