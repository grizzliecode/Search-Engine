package com.octavian.search_engine.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class SearchService {
    private final Logger logger;
    private SearchRepository repository;
    @Autowired
    public SearchService(Logger logger, SearchRepository repository){
        this.logger = logger;
        this.repository = repository;
    }

    public List<SearchModel>getFiles(String query) throws InputMismatchException {
        Map<List<String>,List<String>> payload = QueryParser.parseQuery(query);
        return this.repository.retrieveByQuery(payload);
    }
}
