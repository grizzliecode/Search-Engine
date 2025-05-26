package com.octavian.search_engine.search.widget;

import com.octavian.search_engine.search.SearchModel;

import java.util.List;

public class ExecutableStatisticWidget implements Widget{
    @Override
    public String getType() {
        return "EXE";
    }

    @Override
    public float activationScore(String query, List<SearchModel> results) {
        float score = 0f;
        if(query.contains(".exe"))
            score += 5f;
        for(SearchModel result: results){
            if(result.extension().equals("exe"))
                score += 1f;
        }
        return score;
    }
}
