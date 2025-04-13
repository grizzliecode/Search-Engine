package com.octavian.search_engine.search;

public record SearchModel(Integer file_id, String file_path, String extension, Long file_size,Float entropy, Float rank_score, String lines) {
}
