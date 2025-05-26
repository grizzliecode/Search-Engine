package com.octavian.search_engine.search.widget;

import com.octavian.search_engine.search.SearchModel;

import java.util.List;

public class EmptyWidget implements Widget{
    @Override
    public String getType() {
        return "EMPTY";
    }

    @Override
    public float activationScore(String query, List<SearchModel> results) {
        return 0.5f;
    }
}
