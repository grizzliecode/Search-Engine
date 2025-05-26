package com.octavian.search_engine.search;

import java.util.List;

public class SearchResponse {
    private List<SearchModel> results;
    private String widgetType;

    public SearchResponse(List<SearchModel> results, String message) {
        this.results = results;
        this.widgetType = message;
    }
    public List<SearchModel> getResults() {
        return results;
    }

    public void setResults(List<SearchModel> results) {
        this.results = results;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }
}
