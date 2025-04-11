package com.octavian.search_engine.indexer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

@Component
public class ReaderContext {
    private ContentReader reader = new TextReader();

    public void setReader(String extension) {
        switch (extension) {
            case "pdf": {
                this.reader = new PDFReader();
                break;
            }
            case "docs", "docx": {
                this.reader = new DocsReader();
                break;
            }
            default:
                this.reader = new TextReader();
        }
    }

    public String getContent(Path path) throws IOException {
        return this.reader.getContent(path);
    }
}
