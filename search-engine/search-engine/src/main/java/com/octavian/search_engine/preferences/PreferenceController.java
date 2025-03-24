package com.octavian.search_engine.preferences;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/preferences")
public class PreferenceController{
    private final Logger logger;
    private final PreferenceService pf;
    @Autowired
    public PreferenceController(Logger logger, PreferenceService preferenceService){
        this.logger = logger;
        this.pf = preferenceService;
    }
    @GetMapping("")
    public Preferences getAllPreferences(){
        logger.info("requested preferences");
        return pf.getPreferences();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("")
    public void updatePreferences(@RequestBody Preferences preferences){
        logger.info("Saved preferences" + preferences.toString());
        pf.savePreferences(preferences);
    }
}
