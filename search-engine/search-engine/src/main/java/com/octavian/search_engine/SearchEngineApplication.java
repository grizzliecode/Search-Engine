package com.octavian.search_engine;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class SearchEngineApplication {
	@Autowired
	private Logger logger;
	public static  Logger log;
	@PostConstruct
	void init(){
		log = logger;
	}
	public static void main(String[] args) {
		SpringApplication.run(SearchEngineApplication.class, args);
		log.info("app restarted");
	}

}
