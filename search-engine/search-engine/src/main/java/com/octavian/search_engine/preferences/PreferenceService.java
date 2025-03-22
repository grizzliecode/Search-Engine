package com.octavian.search_engine.preferences;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PreferenceService {
    private Preferences preferences;
    private final Logger logger;
    public PreferenceService(Logger logger){
        this.logger = logger;
    }

    @PostConstruct
    void init(){
        try {
            Stream<String> stream = Files.lines(Paths.get("preferences.csv"));
            List<String> content = stream.map(String::toString).collect(Collectors.toList());
            if(content.isEmpty()){
                this.preferences = null;
                return;
            }
            String root = content.get(0);
            content.remove(root);
            this.preferences = new Preferences(root, content);

        } catch (IOException e) {
            logger.warning("Couldn't read the preferences");
        }
        finally {
            Runtime.getRuntime().addShutdownHook(new Thread(this::save));
        }
    }

    void save() {
        List<String> content = preferences.ignored_paths();
        content.add(0, preferences.path());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("preferences.csv"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
            logger.info("Preferences successfully persisted.");
        } catch (IOException e) {
            logger.warning("Couldn't persist preferences: " + e.getMessage());
        }
    }

    public Preferences getPreferences(){
        return this.preferences;
    }
    public void savePreferences(Preferences pref){
            this.preferences = pref;
    }
}
