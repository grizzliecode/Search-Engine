package com.octavian.search_engine.indexer.file_utilities.metadata;

import com.octavian.search_engine.indexer.IndexModel;
import com.octavian.search_engine.indexer.file_utilities.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ExecutableMetadataExtractor implements MetadataExtractor{
    private static final float ACCESSED_WEIGHT = 0.5f;
    private static final float ENTROPY_WEIGHT = -0.25f;
    private static final float PATH_ENTROPY_WEIGHT = -0.15f;

    @Override
    public IndexModel getMetadata(Path file_path, BasicFileAttributes basicFileAttributes, IndexModel im){
        Instant lastAccessed = basicFileAttributes.lastAccessTime().toInstant();
        float rank_score = 0.0f;
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
        float entropy = 0.0f;
        try {
            entropy = getFileEntropy(file_path);
        } catch (IOException e) {
            entropy = 0.0f;
        }
        rank_score = rank_score * ACCESSED_WEIGHT + entropy * ENTROPY_WEIGHT + FileHandler.getPathEntropy(file_path) * PATH_ENTROPY_WEIGHT;
        return new IndexModel(im.file_id(),
                im.file_path(),
                im.extension(),
                im.file_size(),
                entropy,
                rank_score,
                im.content(),
                im.lines(),
                im.last_modified());
    }

    public static float getFileEntropy(Path path) throws IOException {
        byte[] data = Files.readAllBytes(path);
        int length = data.length;
        if (length == 0) return 0.0f;

        Map<Byte, Integer> histogram = new HashMap<>();
        for (byte b : data) {
            histogram.put(b, histogram.getOrDefault(b, 0) + 1);
        }

        float entropy = 0.0f;

        for (int freq : histogram.values()) {
            float p = (float) freq / length;
            entropy -= (float) (p * (Math.log(p) / Math.log(2)));
        }

        return entropy;
    }
}
