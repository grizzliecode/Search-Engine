package com.octavian.search_engine.indexer.file_utilities.file_reader;

import java.io.IOException;
import java.nio.file.Path;

public class ExecutableReader implements ContentReader{
    @Override
    public String getContent(Path path) throws IOException {
        return "Executable File. Content not provided.";
    }
}
