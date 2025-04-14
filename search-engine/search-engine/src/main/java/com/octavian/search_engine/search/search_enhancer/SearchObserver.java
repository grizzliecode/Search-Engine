package com.octavian.search_engine.search.search_enhancer;

import com.octavian.search_engine.search.SearchModel;

import java.util.List;

public interface SearchObserver {
    public void update(String query, List<SearchModel> results);
}
