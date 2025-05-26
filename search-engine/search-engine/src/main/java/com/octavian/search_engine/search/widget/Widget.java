package com.octavian.search_engine.search.widget;

import com.octavian.search_engine.search.SearchModel;

import java.util.List;

public interface Widget {
    String getType();
    float activationScore(String query, List<SearchModel> results);
}
