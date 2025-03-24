package com.octavian.search_engine.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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

    public List<SearchModel> retrieveByContent(String content) {
        String query = "SELECT f.file_id, f.file_path, f.extension, f.file_size, f.lines FROM files f JOIN files_fts ft ON ft.ts_id = f.file_id WHERE ft.tsv @@ plainto_tsquery('simple',?)";
        try {
            return this.jdbcClient.sql(query).param(content).query(SearchModel.class).list();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

    }

    public List<SearchModel> retrieveByWord(String content) {
        String query = "SELECT f.file_id, f.file_path, f.extension, f.file_size, f.lines FROM files f JOIN files_fts ft ON ft.ts_id = f.file_id WHERE ft.tsv @@ phraseto_tsquery('simple',?)";
        try {
            return this.jdbcClient.sql(query).param(content).query(SearchModel.class).list();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }

    }

    public List<SearchModel> retrieveByMinLength(Long length) {
        try{
            String query = "SELECT f.file_id, f.file_path, f.extension, f.file_size, f.lines FROM files f WHERE f.file_size >= ?";
            return this.jdbcClient.sql(query).param(length).query(SearchModel.class).list();
        } catch (Exception e){
            logger.warning(e.getMessage());
            return null;
        }
    }

    public List<SearchModel> retrieveByExtension(String extension){
        try {
            String query = "SELECT f.file_id, f.file_path, f.extension, f.file_size, f.lines FROM files f WHERE f.extension = ?";
            return this.jdbcClient.sql(query).param(extension).query(SearchModel.class).list();
        } catch (Exception e){
            logger.warning(e.getMessage());
            return null;
        }
    }
}
