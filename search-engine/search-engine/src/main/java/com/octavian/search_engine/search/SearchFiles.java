package com.octavian.search_engine.search;

import java.util.InputMismatchException;
import java.util.List;

public interface SearchFiles {
    public List<SearchModel> getFiles(String query) throws InputMismatchException;
}
