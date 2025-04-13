package com.octavian.search_engine;

import com.octavian.search_engine.search.QueryParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QueryParserTests {
    @Test
    public void testSimpleQuery(){
        String query = "path:A\\B content:C";
        Map<List<String>, List<String>> res = QueryParser.parseQuery(query);
        Map<List<String>, List<String>> expected = new HashMap<>();
        ArrayList<String> paths = new ArrayList<>();
        paths.add("A\\B");
        ArrayList<String> contents = new ArrayList<>();
        contents.add("C");
        expected.put(paths,contents);
        assertEquals(expected,res);
    }

    @Test
    public void testSimpleQueryReverse(){
        String query = "content:C path:A\\B ";
        Map<List<String>, List<String>> res = QueryParser.parseQuery(query);
        Map<List<String>, List<String>> expected = new HashMap<>();
        ArrayList<String> paths = new ArrayList<>();
        paths.add("A\\B");
        ArrayList<String> contents = new ArrayList<>();
        contents.add("C");
        expected.put(paths,contents);
        assertEquals(expected,res);
    }

    @Test
    public void testComplexQuery(){
        String query = "path:A\\B content:C AND D AND E OR content:X AND Y path:R\\T\\W";
        Map<List<String>, List<String>> res = QueryParser.parseQuery(query);
        Map<List<String>, List<String>> expected = new HashMap<>();
        ArrayList<String> paths = new ArrayList<>();
        paths.add("A\\B");
        ArrayList<String> contents = new ArrayList<>();
        contents.add("C");
        contents.add("D");
        contents.add("E");
        expected.put(paths,contents);
        paths = new ArrayList<>();
        contents = new ArrayList<>();
        contents.add("X");
        contents.add("Y");
        paths.add("R\\T\\W");
        expected.put(paths,contents);
        assertEquals(expected,res);
    }

    @Test
    public void testQualifiersWithoutAND(){
        String query = "content:C path:A\\B N\\M\\Y";
        Exception e = assertThrows(InputMismatchException.class, ()->{
            QueryParser.parseQuery(query);
        });
        String message = e.getMessage();
        assertTrue(message.contains("Invalid query format. Duplicate qualifiers have to be combined using 'AND'."));
    }

    @Test
    public void testQualifiersWithoutOR(){
        String query = "content:C path:A\\B AND N\\M\\Y path:D\\R";
        Exception e = assertThrows(InputMismatchException.class, ()->{
            QueryParser.parseQuery(query);
        });
        String message = e.getMessage();
        assertTrue(message.contains("Duplicate qualifiers have to be combined using 'AND' and separated path content pairs should be combined using OR."));
    }

    @Test
    public void testStartWithoutQualifiers(){
        String query = " M AND content:C path:A\\B AND N\\M\\Y path:D\\R";
        Exception e = assertThrows(InputMismatchException.class, ()->{
            QueryParser.parseQuery(query);
        });
        String message = e.getMessage();
        assertTrue(message.contains("Invalid query format. Query should start with qualifiers 'path' or 'content'"));
    }
}
