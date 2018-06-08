package com.zhteee;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ValidationApplication {
	
	private static Logger logger = Logger.getLogger(ValidationApplication.class);

	/**
     * Start
     */
	public static void main(String[] args) {
		new SpringApplicationBuilder(ValidationApplication.class).web(true).run(args);
		logger.info("SpringBoot Start Success !");
	}
}
