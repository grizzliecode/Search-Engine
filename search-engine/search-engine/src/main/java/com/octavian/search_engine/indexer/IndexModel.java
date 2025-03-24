package com.octavian.search_engine.indexer;

public record IndexModel(Integer file_id, String file_path, String extension, Long file_size, String content, String lines ) {
}
