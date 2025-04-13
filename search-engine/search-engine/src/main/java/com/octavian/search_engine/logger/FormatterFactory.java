package com.octavian.search_engine.logger;

import java.util.logging.Formatter;

public class FormatterFactory {
    public static Formatter createFormatter(String format){
        return switch (format.toLowerCase()) {
            case "json" -> new JsonFormatter();          // your custom one
            case "txt", "plain" -> new java.util.logging.SimpleFormatter();
            default -> throw new IllegalArgumentException("Unsupported log format: " + format);
        };
    }
}
