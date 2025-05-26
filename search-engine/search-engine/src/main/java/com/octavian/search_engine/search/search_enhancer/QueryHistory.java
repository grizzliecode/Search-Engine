package com.octavian.search_engine.search.search_enhancer;

import com.octavian.search_engine.search.Cache;
import com.octavian.search_engine.search.SearchModel;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class QueryHistory implements SearchObserver{

    private Cache history;
    @Autowired
    public QueryHistory(Cache cache) {
        this.history = cache;
    }
    @Override
    public synchronized void update(String query, List<SearchModel> results) {
        float average_rank = 0.0f;
        int count = 0;
        for(SearchModel result: results){
            count++;
            average_rank+=result.rank_score();
        }
        this.history.put(query,new Tuple<>(average_rank/count,results));
    }

    public synchronized List<String> suggestQueries(String s){
        List<String> s1 = new ArrayList<>(history.keySet().stream()
                .filter(q -> q.toLowerCase().contains(s.toLowerCase()))
                .sorted((q1, q2) -> (history.get(q1)._1() - history.get(q2)._1() >= 0 ? -1 : 1))
                .limit(5)
                .toList());
        if(s1.size() < 5){
            int size = 5 - s1.size();
            List<String> s2 = history.keySet().stream()
                    .filter(q->!q.toLowerCase().contains(s.toLowerCase()))
                    .sorted((q1,q2)->  (history.get(q1)._1() - history.get(q2)._1() >= 0 ? -1 : 1))
                    .limit(size)
                    .toList();
            s1.addAll(s2);
        }
        return s1;
    }
    private String findLongestCommonSubstring(String s1, String s2) {
        int maxLen = 0;
        int endIndexS1 = 0;
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        endIndexS1 = i;
                    }
                }
            }
        }
        return s1.substring(endIndexS1 - maxLen, endIndexS1);
    }

    private int compare(float f1, float f2){
        if(Math.abs(f1 - f2) < 0.0001) return 0;
        if(f1 - f2 > 0 ) return -1;
        return 1;
    }
    public synchronized List<SearchModel> rankResult(List<SearchModel> results){
         Map<SearchModel, Float> scoreboard = new HashMap<>();
         for(SearchModel result: results){
             int count = 0;
             float score = 0.0f;
             for(Tuple<Float, List<SearchModel>> entry: history.values()){
                 for(SearchModel prevResult: entry._2()){
                     count++;
                     String longest_substring = findLongestCommonSubstring(result.file_path(),prevResult.file_path());
                     score += (float) longest_substring.length() /prevResult.file_path().length();
                 }
             }
             scoreboard.put(result,score/count);
         }
         return scoreboard.keySet().stream()
                 .sorted((s1,s2)-> compare(scoreboard.get(s1), scoreboard.get(s2)))
                 .limit(15)
                 .collect(Collectors.toList());
    }
}
