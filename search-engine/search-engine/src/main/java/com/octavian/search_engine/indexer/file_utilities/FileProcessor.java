package com.octavian.search_engine.indexer.file_utilities;

import com.octavian.search_engine.indexer.IndexModel;
import com.octavian.search_engine.indexer.file_utilities.file_reader.ContentReader;
import com.octavian.search_engine.indexer.file_utilities.file_reader.ReaderContext;
import com.octavian.search_engine.indexer.file_utilities.metadata.MetadataContext;
import com.octavian.search_engine.indexer.file_utilities.metadata.MetadataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.logging.Logger;

@Component
public class FileProcessor {
    private static final int LINES_LENGTH = 240;
    @Autowired
    private Logger logger;
    private ReaderContext readerContext = new ReaderContext();
    private MetadataContext metadataContext = new MetadataContext();

    public IndexModel processFile(Path file_path, BasicFileAttributes basicFileAttributes) throws IOException {
        readerContext.setReader(FileHandler.getExtension(file_path.toString()));
        metadataContext.setMetadataExtractor(FileHandler.getExtension(file_path.toString()));
        String content = readerContext.getContent(file_path);
        String extension = FileHandler.getExtension(file_path.toString());
        Long size = (Long) basicFileAttributes.size();
        String path = file_path.toAbsolutePath().toString();
        String lines = content.length() <= LINES_LENGTH ? content : content.substring(0, LINES_LENGTH);
        Instant last_modified = basicFileAttributes.lastModifiedTime().toInstant();
        IndexModel im = new IndexModel(null, file_path.toString(), extension,size,null,null, content, lines,last_modified);
        return metadataContext.process(file_path, basicFileAttributes, im);
    }


}
