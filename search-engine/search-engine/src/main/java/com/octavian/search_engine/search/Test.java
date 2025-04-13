package com.octavian.search_engine.search;

import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String query = "path:FLT  AND Le ";
//        String[] parts = query.split("\\s+OR\\s+");
//        for (String part : parts) {
//            System.out.println(part);
//            for (String token : part.split("\\s+")) {
//                System.out.println("1:" + token);
//            }
//        }
        Map<List<String>, List<String>> res = QueryParser.parseQuery(query);
        System.out.println(res);
    }
}
