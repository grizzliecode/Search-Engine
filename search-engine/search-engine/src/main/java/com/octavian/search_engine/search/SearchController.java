package com.octavian.search_engine.search;

import com.octavian.search_engine.search.widget.WidgetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {

    private final Logger logger;
    private final SearchService searchService;
    private SearchCache searchCache;
    private WidgetFactory widgetFactory;

    @Autowired
    public SearchController(Logger logger, SearchService searchService, SearchCache searchCache, WidgetFactory widgetFactory) {
        this.logger = logger;
        this.searchService = searchService;
        this.searchCache = searchCache;
        this.widgetFactory = widgetFactory;
    }

    //    @GetMapping("/by-content")
//    @ResponseStatus(HttpStatus.OK)
//    public List<SearchModel> getByContent(@RequestParam String content) {
//        this.logger.info("A get request was generated to get all files with containing the content" + content);
//        String[] words = content.split("\\s+");
//        return (words.length > 1) ? this.repository.retrieveByContent(content) : this.repository.retrieveByWord(content);
//    }
//
//    @GetMapping("/by-extension")
//    @ResponseStatus(HttpStatus.OK)
//    public List<SearchModel> getByExtension(@RequestParam String extension) {
//        this.logger.info("A get request was generated to get all files with the extension" + extension);
//        return this.repository.retrieveByExtension(extension);
//    }
//
//    @GetMapping("by-size{min_size}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<SearchModel> getByMinSize(@PathVariable Long min_size) {
//        this.logger.info("A get request was generated to get all files with the size greater then" + min_size);
//        return this.repository.retrieveByMinLength(min_size);
//    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> searchFiles(@RequestParam String query) {
        try {
            List<SearchModel> result = this.searchCache.getFiles(query);
            String widgetType = this.widgetFactory.getBestFit(query,result).getType();
            SearchResponse response = new SearchResponse(result, widgetType);
            return ResponseEntity.ok(response);
        } catch (InputMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        }
    }


    @GetMapping("/suggestion")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSuggestions(@RequestParam String partial){
        return this.searchService.getSuggestions(partial);
    }

    @PostMapping("/open")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> openFile(@RequestParam String file_path) {
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
