CREATE TABLE IF NOT EXISTS files (
                       file_id SERIAL PRIMARY KEY,
                       file_path TEXT UNIQUE NOT NULL,
                       extension TEXT NOT NULL,
                       file_size BIGINT NOT NULL,
                       content TEXT,
                       lines TEXT,
                       last_modified TIMESTAMP
);
CREATE TABLE IF NOT EXISTS files_fts (
                        ts_id INTEGER PRIMARY KEY REFERENCES files(file_id) ON DELETE CASCADE,
                        tsv tsvector
);

CREATE INDEX IF NOT EXISTS files_fts_idx ON files_fts USING GIN(tsv);
CREATE INDEX IF NOT EXISTS idx_extension ON files(extension);