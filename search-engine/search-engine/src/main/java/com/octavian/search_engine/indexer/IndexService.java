package com.octavian.search_engine.indexer;

import com.octavian.search_engine.indexer.file_utilities.FileHandler;
import com.octavian.search_engine.indexer.file_utilities.FileProcessor;
import com.octavian.search_engine.indexer.file_utilities.file_reader.ReaderContext;
import com.octavian.search_engine.preferences.PreferenceService;
import com.octavian.search_engine.preferences.Preferences;
import org.apache.poi.EmptyFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.logging.Logger;

@Service
public class IndexService {
    private final int LINES_LENGTH = 120;
    private final Logger logger;
    private final PreferenceService pref;
    private final IndexRepository repository;

    @Autowired
    IndexService(Logger logger, PreferenceService preferenceService, IndexRepository indexRepository1) {
        this.logger = logger;
        this.pref = preferenceService;
        this.repository = indexRepository1;
    }

    public void saveIM(IndexModel im) {
        this.repository.saveModel(im);
    }

    public void deleteRecords() {
        this.repository.dropTables();
    }



    public void traverse() {
        Preferences preferences = pref.getPreferences();
        Path startPath = Paths.get(preferences.path());
        FileProcessor fileProcessor = new FileProcessor();
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (FileHandler.isInside(preferences.ignored_paths(), dir.toString()) || attrs.isSymbolicLink()) {
                        logger.info("Ingnored " + dir);
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
                    logger.info("File: " + file);
                    if(!FileHandler.isText(file,logger)){
                        if(!FileHandler.isExtensionSupported(FileHandler.getExtension(file.toString()))){
                            logger.info("File format not supported" + FileHandler.getExtension(file.toString()));
                            return FileVisitResult.CONTINUE;
                        }
                    }
                    try {
                        IndexModel im = fileProcessor.processFile(file,attrs);
                        repository.saveModel(im);
                    } catch (IOException | EmptyFileException e) {
                        logger.warning(e.toString() +  FileHandler.getExtension(file.toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    logger.info("Access Denied: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    logger.info("Leaving Directory: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.info("Error while trying to parse the tre" + e.toString());
        }

    }


}
