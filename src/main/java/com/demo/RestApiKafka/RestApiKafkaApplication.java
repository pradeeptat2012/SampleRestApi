package com.demo.RestApiKafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.restapi.controller.ProductController;

@SpringBootApplication
public class RestApiKafkaApplication {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RestApiKafkaApplication.class);

	public static void main(String[] args) {
		
		LOGGER.info("Inside Main Method of Spring Boot Application");
		
		SpringApplication.run(RestApiKafkaApplication.class, args);
	}

}
