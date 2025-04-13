package com.octavian.search_engine.search;


import java.util.*;

public class QueryParser {

    public static Map<List<String>, List<String>> parseQuery(String query) throws InputMismatchException {
        Map<List<String>,List<String>> result = new HashMap<>();
        String[] parts = query.split("\\s+OR\\s+");
        for (String part : parts) {
            List<String> paths = new ArrayList<>();
            List<String> contents = new ArrayList<>();
            int i = 0, order = 0;
            for (String token : part.split("\\s+")) {
                if (token.startsWith("path:")) {
                    if (!paths.isEmpty()) {
                        throw new InputMismatchException("Exception in parsing query:" + query + ". Invalid query format. Duplicate qualifiers have to be combined using 'AND' and separated path content pairs should be combined using OR.");
                    }
                    i = 1;
                    String p = token.substring(5);
                    paths.add(p);
                    order = 0;
                }else if (token.startsWith("content:")) {
                    i = 2;
                    if (!contents.isEmpty()) {
                        throw new InputMismatchException("Exception in parsing query:" + query + ". Invalid query format. Duplicate qualifiers have to be combined using 'AND' and separated path content pairs should be combined using OR.");
                    }
                    String c = token.substring(8);
                    contents.add(c);
                    order = 0;
                } else {
                    if (order == 1 && !token.equals("AND")) {
                        throw new InputMismatchException("Exception in parsing query:" + query + ". Invalid query format. Duplicate qualifiers have to be combined using 'AND'.");
                    } else if (order == 0) {
                        if (i == 0) {
                            throw new InputMismatchException("Exception in parsing query:" + query + ". Invalid query format. Query should start with qualifiers 'path' or 'content'");
                        } else if (i == 1) {
                            paths.add(token);
                        } else {
                            contents.add(token);
                        }
                    }
                }
                order = 1 - order;
            }
            result.put(paths,contents);
        }
        return result;
    }
}
