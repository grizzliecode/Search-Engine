package com.octavian.search_engine.search;

import com.octavian.search_engine.search.search_enhancer.QueryHistory;
import com.octavian.search_engine.search.search_enhancer.SearchPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class SearchService implements SearchFiles {
    private final Logger logger;
    private SearchRepository repository;

    private final QueryHistory queryHistory;
    private final SearchPublisher searchPublisher = new SearchPublisher();
    @Autowired
    public SearchService(Logger logger, SearchRepository repository, QueryHistory queryHistory){
        this.logger = logger;
        this.repository = repository;
        this.queryHistory = queryHistory;
        this.searchPublisher.addObserver(this.queryHistory);
    }

    public List<SearchModel> getFiles(String query) throws InputMismatchException {
        Map<List<String>,List<String>> payload = QueryParser.parseQuery(query);
        List<SearchModel> results = this.repository.retrieveByQuery(payload);
        results = queryHistory.rankResult(results);
        searchPublisher.notifyObservers(query, results);
        return results;
    }

    public List<String> getSuggestions(String partial){
        return queryHistory.suggestQueries(partial);
    }
}
