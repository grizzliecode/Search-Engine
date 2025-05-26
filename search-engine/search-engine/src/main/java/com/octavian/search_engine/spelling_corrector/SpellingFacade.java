package com.octavian.search_engine.spelling_corrector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class SpellingFacade {
    private final SpellingService spellingService;

    @Autowired
    public SpellingFacade(SpellingService spellingService) {
        this.spellingService = spellingService;
    }
    public String correctSpelling(String input) {
        String[] tokens = input.split("[:\\s]+");
        List<String> words = Arrays.asList(tokens);
        List<String> new_words = new ArrayList<>();
        words = words.stream().filter(x -> !x.equals("path") && !x.equals("content") && !x.equals("AND") && !x.equals("OR")).collect(Collectors.toList());
        for(String word : words){
            new_words.add(this.spellingService.correct(word));
        }
        System.out.println(words);
        for(int i = 0; i < words.size(); i++){
            input = input.replace(words.get(i), new_words.get(i));
        }
        return input;
    }
}
