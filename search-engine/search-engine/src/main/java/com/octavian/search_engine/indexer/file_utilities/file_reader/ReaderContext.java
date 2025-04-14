package com.octavian.search_engine.indexer.file_utilities.file_reader;

import org.apache.poi.EmptyFileException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

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
            case "exe":{
                this.reader = new ExecutableReader();
                break;
            }
            default:
                this.reader = new TextReader();
        }
    }

    public String getContent(Path path) throws IOException, EmptyFileException {
        return this.reader.getContent(path);
    }
}
