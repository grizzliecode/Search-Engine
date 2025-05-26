package com.octavian.search_engine.search;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;

import javax.naming.directory.SearchResult;
import java.util.*;

@Component
public class Cache {
    private int MAX = 10;
    private Map<String, Tuple<Float, List<SearchModel>>> history;
    public Cache(){
        this.history= new LinkedHashMap<String, Tuple<Float, List<SearchModel>>>(MAX, 1, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Tuple<Float, List<SearchModel>>> eldest) {
                return size() > MAX;
            }
        };
    }

    public List<SearchModel> check(String query){
        if(history.containsKey(query))
            return history.get(query)._2();
        else return null;
    }
    public Tuple<Float,List<SearchModel> >get(String key){
            return this.history.get(key);
    }

    public Set<String> keySet(){
        return this.history.keySet();
    }

    public Collection<Tuple<Float, List<SearchModel>>> values(){
        return this.history.values();
    }
    public void put(String key, Tuple<Float,List<SearchModel>> results){
        this.history.put(key, results);
    }
}
