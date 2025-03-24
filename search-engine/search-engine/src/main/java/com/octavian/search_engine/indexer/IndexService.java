package com.octavian.search_engine.indexer;

import com.octavian.search_engine.preferences.PreferenceService;
import com.octavian.search_engine.preferences.Preferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class IndexService {
    private final int LINES_LENGTH = 120;
    private List<ContentReader> readers;
    private final Logger logger;
    private final PreferenceService pref;
    private final IndexRepository repository;

    @Autowired
    IndexService(Logger logger, PreferenceService preferenceService, IndexRepository indexRepository1) {
        this.logger = logger;
        this.pref = preferenceService;
        this.repository = indexRepository1;
        this.readers = new ArrayList<>();
        this.readers.add(new TextReader());
        this.readers.add(new DocsReader());
        this.readers.add(new PDFReader());
    }

    public void saveIM(IndexModel im) {
        this.repository.saveModel(im);
    }

    public void deleteRecords() {
        this.repository.dropTables();
    }

    public ContentReader getContentReader(String extension) {
        return switch (extension) {
            case "docs" -> this.readers.get(1);
            case "docx" -> this.readers.get(1);
            case "pdf" -> this.readers.get(2);
            default -> this.readers.get(0);
        };
    }

    boolean isInside(List<String> ignored, String path) {
        return ignored.contains(path);
    }

    public void traverse() {
        Preferences preferences = pref.getPreferences();
        Path startPath = Paths.get(preferences.path());

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                ContentReader contentReader = getContentReader("");

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (isInside(preferences.ignored_paths(), dir.toString())) {
                        logger.info("Ingnored " + dir);
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    logger.info("File: " + file);
                    contentReader = getContentReader(getExtension(file.toString()));
                    try {
                        String content = contentReader.getContent(file);
                        String extension = getExtension(file.toString());
                        Long size = (Long) attrs.size();
                        String path = file.toAbsolutePath().toString();
                        String lines = content.length() <= LINES_LENGTH ? content : content.substring(0, LINES_LENGTH);
                        IndexModel im = new IndexModel(null, path, extension, size, content, lines);
                        saveIM(im);
                    } catch (IOException e) {
                        logger.warning(e.toString());
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

    public String getExtension(String path) {
        return com.google.common.io.Files.getFileExtension(path);
    }

    public boolean isText(Path path) {
        String type = null;
        try {
            type = Files.probeContentType(path);
        } catch (IOException e) {
            logger.warning("Couldn't find the type of file " + path.getFileName());
        }
        if (type != null && type.startsWith("text")) {
            return true;
        }
        return false;
    }
}
