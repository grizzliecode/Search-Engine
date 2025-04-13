package com.octavian.search_engine.indexer.file_utilities.metadata;

import com.octavian.search_engine.indexer.IndexModel;
import com.octavian.search_engine.indexer.file_utilities.FileHandler;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;

public class TextFileMetadataExtractor implements MetadataExtractor{
    private static final float ACCESSED_WEIGHT = 0.20f;
    private static final float PATH_ENTROPY_WEIGHT = -0.30f;
    @Override
    public IndexModel getMetadata(Path file_path, BasicFileAttributes basicFileAttributes, IndexModel im){
        Instant lastAccessed = basicFileAttributes.lastAccessTime().toInstant();
        float rank_score = 0f;
        Instant now = Instant.now();
        Instant oneDayAgo = now.minus(Duration.ofDays(1));
        Instant oneWeekAgo = now.minus(Duration.ofDays(7));
        Instant oneMonthAgo = now.minus(Duration.ofDays(30));

        if (lastAccessed.isAfter(oneDayAgo)) {
            rank_score = 5.0f;
        } else if (lastAccessed.isAfter(oneWeekAgo)) {
            rank_score = 3.0f;
        } else if (lastAccessed.isAfter(oneMonthAgo)) {
            rank_score = 1.0f;
        } else {
            rank_score = 0.5f;
        }
        rank_score = ACCESSED_WEIGHT*rank_score+PATH_ENTROPY_WEIGHT*FileHandler.getPathEntropy(file_path);
        return new IndexModel(im.file_id(),
                im.file_path(),
                im.extension(),
                im.file_size(),
                im.entropy(),
                rank_score,
                im.content(),
                im.lines(),
                im.last_modified());
    }
}
