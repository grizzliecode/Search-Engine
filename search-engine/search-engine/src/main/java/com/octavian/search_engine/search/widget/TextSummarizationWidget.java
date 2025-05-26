package com.octavian.search_engine.search.widget;

import com.octavian.search_engine.search.SearchModel;

import java.util.List;

public class TextSummarizationWidget implements Widget{

    @Override
    public String getType() {
        return "TEXT";
    }

    @Override
    public float activationScore(String query, List<SearchModel> results) {
        float score = 0f;
        if(query.contains(".pdf") || query.contains(".docs") || query.contains(".txt")){
            score += 5;
        }
        for(SearchModel result : results){
            if(result.extension().equals("pdf") || result.extension().equals("docs") || result.extension().equals("txt"))
                score += 1;
        }
        return score;
    }
}
