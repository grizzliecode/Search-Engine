package com.octavian.search_engine.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class JsonFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return String.format(
                "{\"level\": \"%s\", \"logger\": \"%s\", \"message\": \"%s\", \"timestamp\": \"%d\"}%n",
                record.getLevel(),
                record.getLoggerName(),
                record.getMessage(),
                record.getMillis()
        );
    }
}
