package com.octavian.search_engine.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Repository
public class SearchRepository {
    private final JdbcClient jdbcClient;
    private final Logger logger;

    @Autowired
    public SearchRepository(JdbcClient jdbcClient1, Logger logger) {
        this.jdbcClient = jdbcClient1;
        this.logger = logger;
    }

//    public List<SearchModel> retrieveByContent(String content) {
//        String query = "SELECT f.file_id, f.file_path, f.extension, f.file_size, f.lines FROM files f JOIN files_fts ft ON ft.ts_id = f.file_id WHERE ft.tsv @@ plainto_tsquery('simple',?)";
//        try {
//            return this.jdbcClient.sql(query).param(content).query(SearchModel.class).list();
//        } catch (Exception e) {
//            logger.warning(e.getMessage());
//            return null;
//        }
//
//    }

//    public List<SearchModel> retrieveByWord(String content) {
//        String query = "SELECT f.file_id, f.file_path, f.extension, f.file_size, f.lines FROM files f JOIN files_fts ft ON ft.ts_id = f.file_id WHERE ft.tsv @@ phraseto_tsquery('simple',?)";
//        try {
//            return this.jdbcClient.sql(query).param(content).query(SearchModel.class).list();
//        } catch (Exception e) {
//            logger.warning(e.getMessage());
//            return null;
//        }
//
//    }

    public List<SearchModel> retrieveByQuery(Map<List<String>, List<String>> payload) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        for (Map.Entry<List<String>, List<String>> entry : payload.entrySet()) {
            List<String> pathParts = entry.getKey();
            List<String> contentWords = entry.getValue();
            String pathPattern = "%" + String.join("%", pathParts) + "%";
            String tsQuery = String.join(" & ", contentWords);
            if (!sql.isEmpty()) sql.append(" UNION ");
            sql.append("""
            SELECT file_id, file_path, extension, file_size, entropy, rank_score, lines
            FROM files
            WHERE 
        """);
            if(!pathParts.isEmpty()){
                sql.append("file_path ILIKE ?");
                params.add(pathPattern);
            }
            if(!contentWords.isEmpty()){
                if(!pathParts.isEmpty())
                    sql.append(" AND ");
                sql.append("to_tsvector('simple', coalesce(content, '')) @@ to_tsquery('simple', ?)");
                params.add(tsQuery);
            }
        }
        sql.append(" ORDER BY rank_score DESC LIMIT 30");
        return jdbcClient.sql(sql.toString())
                .params(params)
                .query(SearchModel.class)
                .list();
    }


}
