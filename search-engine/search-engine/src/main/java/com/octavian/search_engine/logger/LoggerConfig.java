package com.octavian.search_engine.logger;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@Configuration
public class LoggerConfig {
    private final org.slf4j.Logger log = LoggerFactory.getLogger("Global Logger");
    @Bean
    public Logger getCustomLogger(){
        Logger logger = Logger.getLogger("com.octavian.search-engine");
        try{
            FileHandler fileHandler = new FileHandler("logs.txt");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        }
        catch (IOException e)
        {
            log.info(e.toString());
        }
        return logger;
    }

}
