package com.octavian.search_engine.indexer.file_utilities.metadata;

import com.octavian.search_engine.indexer.IndexModel;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class MetadataContext {
    private MetadataExtractor metadataExtractor;

    public void setMetadataExtractor(String extension) {
        if (extension.equals("exe")) {
            this.metadataExtractor = new ExecutableMetadataExtractor();
        } else {
            this.metadataExtractor = new TextFileMetadataExtractor();
        }
    }

    public IndexModel process(Path file_path, BasicFileAttributes basicFileAttributes, IndexModel im){
        return this.metadataExtractor.getMetadata(file_path, basicFileAttributes,im);
    }
}
