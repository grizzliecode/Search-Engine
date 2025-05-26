package com.octavian.search_engine.spelling_corrector;

import com.octavian.search_engine.search.SearchController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SpellingController {
    private SpellingFacade corrector;

    public SpellingController(SpellingFacade spellingFacade){
        this.corrector = spellingFacade;
    }

    @GetMapping("correct")
    public String getCorrected(@RequestParam String query){
        return this.corrector.correctSpelling(query);
    }
}
