package com.octavian.search_engine.search;

import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
public class SearchCache implements SearchFiles{

    private SearchFiles service;
    private Cache history;
    @Autowired
    public SearchCache(SearchService searchService, Cache cache){
        this.service = searchService;
        this.history = cache;
    }
    @Override
    public List<SearchModel> getFiles(String query) throws InputMismatchException {
        List<SearchModel> result = this.history.check(query);
        if(result != null){
            return result;
        }
        else
        {
            return this.service.getFiles(query);
        }
    }
}
