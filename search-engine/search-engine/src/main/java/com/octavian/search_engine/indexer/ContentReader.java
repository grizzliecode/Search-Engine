package com.octavian.search_engine.indexer;

import java.io.IOException;
import java.nio.file.Path;

public interface ContentReader {
    public String getContent(Path path) throws IOException;
}
