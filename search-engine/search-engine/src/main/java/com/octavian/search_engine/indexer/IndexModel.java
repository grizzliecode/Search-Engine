package com.octavian.search_engine.indexer;

import java.time.Instant;

public record IndexModel(Integer file_id, String file_path, String extension, Long file_size,Float entropy, Float rank_score, String content, String lines, Instant last_modified) {

}
