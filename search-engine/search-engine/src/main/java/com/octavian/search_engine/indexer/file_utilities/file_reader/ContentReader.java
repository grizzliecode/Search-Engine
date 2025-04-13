package com.octavian.search_engine.indexer.file_utilities.file_reader;

import java.io.IOException;
import java.nio.file.Path;

public interface ContentReader {
    public String getContent(Path path) throws IOException;
}
