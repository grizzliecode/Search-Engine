package com.octavian.search_engine.indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextReader implements ContentReader{

    @Override
    public String getContent(Path path) throws IOException {
        return Files.readString(path);
    }
}
