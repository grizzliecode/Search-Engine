package com.octavian.search_engine.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final Logger logger;

    @Autowired
    public SearchController(Logger logger) {
        this.logger = logger;
    }

    @GetMapping("/by-content")
    public List<SearchModel> getByContent() {
        return null;
    }

    @GetMapping("/by-extension")
    public List<SearchModel> getByExtension() {
        return null;
    }

    @GetMapping("by-size{min_size}")
    public List<SearchModel> getByMinSize(@PathVariable Long min_size) {
        return null;
    }

    @PostMapping("/open")
    public ResponseEntity<String> openFile(@RequestBody String file_path) {
        File file = new File(file_path);
        logger.info(file_path);
        logger.info(String.valueOf(file.exists()));
        if (file.exists()) {
            try {
                new ProcessBuilder("explorer.exe", file.getAbsolutePath()).start();
                return new ResponseEntity<>("File opened successfully", HttpStatus.OK);
            } catch (IOException e) {
                logger.warning("Error at opening the file " + file_path + " using the process builder module\n" + e.getMessage());
            }
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
            logger.info("File: " + file_path + "  not found");
            return new ResponseEntity<>("File does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
