CREATE TABLE IF NOT EXISTS files (
                       file_id SERIAL PRIMARY KEY,
                       file_path TEXT UNIQUE NOT NULL,
                       extension TEXT NOT NULL,
                       file_size BIGINT NOT NULL,
                       entropy FLOAT,
                       rank_score FLOAT,
                       lines TEXT,
                       last_modified TIMESTAMP,
                       content TEXT

);


CREATE INDEX IF NOT EXISTS  idx_content ON files USING GIN(to_tsvector('simple', coalesce(content, '')));
CREATE INDEX IF NOT EXISTS idx_extension ON files(extension);

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX IF NOT EXISTS gm_index_path ON files USING gin (lower(file_path) gin_trgm_ops);