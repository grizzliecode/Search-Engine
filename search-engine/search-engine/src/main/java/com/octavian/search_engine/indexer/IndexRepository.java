package com.octavian.search_engine.indexer;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class IndexRepository {

    private final JdbcClient jdbcClient;
    private final Logger logger;

    @Autowired
    public IndexRepository(JdbcClient jdbcClient, Logger logger) {
        this.jdbcClient = jdbcClient;
        this.logger = logger;
    }

    public void saveModel(IndexModel im) {
        try {
            int col = jdbcClient.sql("INSERT INTO files(file_path, extension,file_size, content,  lines) VALUES(?,?,?,?,?)")
                    .param(im.file_path())
                    .param(im.extension())
                    .param(im.file_size())
                    .param(im.content())
                    .param(im.lines())
                    .update();
            if (col == 0) throw new Exception("Record of file failed to be inserted into the table");
            col = jdbcClient.sql("INSERT INTO files_fts(ts_id, tsv) SELECT file_id, to_tsvector('simple',content) FROM files WHERE file_path = ?")
                    .param(im.file_path())
                    .update();
            if (col == 0) throw new Exception("Record of tsv_file failed to be inserted into the table");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void dropTables() {
        try {
            int col = jdbcClient.sql("TRUNCATE TABLE files, files_fts CASCADE").update();
            if (col == 0) throw new Exception("Failed to delet the records of the files");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
}
