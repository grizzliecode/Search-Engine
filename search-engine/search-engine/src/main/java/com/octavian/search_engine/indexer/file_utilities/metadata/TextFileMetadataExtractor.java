package com.octavian.search_engine.indexer.file_utilities.metadata;

import com.octavian.search_engine.indexer.IndexModel;
import com.octavian.search_engine.indexer.file_utilities.FileHandler;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

public class TextFileMetadataExtractor implements MetadataExtractor{
    private static final float ACCESSED_WEIGHT = 0.2f;
    private static final float PATH_ENTROPY_WEIGHT = -0.65f;
    private static final float PATH_LENGTH_WEIGHT = 0.2f;
    @Override
    public IndexModel getMetadata(Path file_path, BasicFileAttributes basicFileAttributes, IndexModel im){
        Instant lastModified = basicFileAttributes.lastModifiedTime().toInstant();
        float rank_score = 0f;
        Instant now = Instant.now();
        Instant oneDayAgo = now.minus(Duration.ofDays(1));
        Instant oneWeekAgo = now.minus(Duration.ofDays(7));
        Instant oneMonthAgo = now.minus(Duration.ofDays(30));
        float length_score = 0f;
        file_path = file_path.toAbsolutePath();
        if(file_path.getNameCount() < 5){
            length_score = 3;
        } else if (file_path.getNameCount() < 10) {
            length_score = 1.5f;
        }
        else length_score = 0.5f;
        if (lastModified.isAfter(oneDayAgo)) {
            rank_score = 5.0f;
        } else if (lastModified.isAfter(oneWeekAgo)) {
            rank_score = 4.0f;
        } else if (lastModified.isAfter(oneMonthAgo)) {
            rank_score = 3.0f;
        } else {
            rank_score = 1.5f;
        }
        System.out.println(length_score);
        System.out.println(file_path.getNameCount());
        System.out.println(rank_score);
        rank_score = ACCESSED_WEIGHT*rank_score+PATH_ENTROPY_WEIGHT*FileHandler.getPathEntropy(file_path) + 5.0f + length_score * PATH_LENGTH_WEIGHT;
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
