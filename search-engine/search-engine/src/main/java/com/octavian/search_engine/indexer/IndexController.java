package com.octavian.search_engine.indexer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/index")
public class IndexController {
    private Logger logger;
    private IndexService indexService;

    @Autowired
    public IndexController(Logger logger, IndexService indexService1) {
        this.logger = logger;
        this.indexService = indexService1;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void startIndexer() {
        logger.info("Indexerer started");
        indexService.deleteRecords();
        indexService.traverse();
    }

    @DeleteMapping("/delete")
    public void deleteRecords() {
        logger.info("records deleted");
        indexService.deleteRecords();
    }
}
