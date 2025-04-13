package com.octavian.search_engine.indexer.file_utilities.metadata;

import com.octavian.search_engine.indexer.IndexModel;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public interface MetadataExtractor {
    public IndexModel getMetadata(Path file_path, BasicFileAttributes attributes, IndexModel im);
}
