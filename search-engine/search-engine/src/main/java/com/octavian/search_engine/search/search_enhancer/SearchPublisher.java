package com.octavian.search_engine.search.search_enhancer;

import com.octavian.search_engine.search.SearchModel;

import java.util.ArrayList;
import java.util.List;

public class SearchPublisher {
    private final List<SearchObserver> observers = new ArrayList<>();

    public void addObserver(SearchObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SearchObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String query, List<SearchModel> results) {
        for (SearchObserver observer : observers) {
            observer.update(query,results);
        }
    }
}
