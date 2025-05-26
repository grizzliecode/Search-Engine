package com.octavian.search_engine.spelling_corrector;

import com.octavian.search_engine.preferences.Preferences;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SpellingService {
    private final Map<String, Integer> wordFreqs;
    String filePath = "words.csv";
    public SpellingService() {
        this.wordFreqs = loadFrequenciesFromCSV(filePath);
    }

    private Map<String, Integer> loadFrequenciesFromCSV(String filePath) {
        Map<String, Integer> freqs = new HashMap<>();
        try {
            Stream<String> stream = Files.lines(Paths.get("words.csv"));
            List<String> content = stream.map(String::toString).collect(Collectors.toList());
            String root = content.get(0);
            content.remove(root);
            for(String line: content){
                String[] parts = line.split(",");
                Integer f = Integer.valueOf(parts[1]);
                freqs.put(parts[0],f);
            }
        } catch (IOException e) {
            System.out.println("Couldn't read the preferences");
        }
        return freqs;
    }

    public String correct(String word) {
        return candidates(word).stream()
                .max(Comparator.comparingInt(this::frequency))
                .orElse(word);
    }

    private int frequency(String word) {
        return wordFreqs.getOrDefault(word, 0);
    }

    private Set<String> candidates(String word) {
        Set<String> knownWords = known(Set.of(word));
        if (!knownWords.isEmpty()) return knownWords;

        Set<String> edits1 = edits1(word);
        knownWords = known(edits1);
        if (!knownWords.isEmpty()) return knownWords;

        Set<String> edits2 = new HashSet<>();
        for (String e1 : edits1) {
            edits2.addAll(edits1(e1));
        }
        knownWords = known(edits2);
        return knownWords.isEmpty() ? Set.of(word) : knownWords;
    }

    private Set<String> known(Set<String> words) {
        Set<String> result = new HashSet<>();
        for (String w : words) {
            if (wordFreqs.containsKey(w)) {
                result.add(w);
            }
        }
        return result;
    }

    private Set<String> edits1(String word) {
        Set<String> result = new HashSet<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < word.length(); i++) {
            result.add(word.substring(0, i) + word.substring(i + 1)); // delete
        }
        for (int i = 0; i < word.length() - 1; i++) {
            result.add(word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2)); // transpose
        }
        for (int i = 0; i < word.length(); i++) {
            for (char c : alphabet.toCharArray()) {
                result.add(word.substring(0, i) + c + word.substring(i + 1)); // replace
            }
        }
        for (int i = 0; i <= word.length(); i++) {
            for (char c : alphabet.toCharArray()) {
                result.add(word.substring(0, i) + c + word.substring(i)); // insert
            }
        }
        return result;
    }
}
