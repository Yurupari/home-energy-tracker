package com.yurupari.insight_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yurupari.insight_service", "com.yurupari.common_data"})
public class InsightServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsightServiceApplication.class, args);
	}

}
