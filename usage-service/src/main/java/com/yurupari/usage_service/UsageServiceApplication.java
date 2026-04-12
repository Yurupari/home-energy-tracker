package com.yurupari.usage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yurupari.usage_service", "com.yurupari.common_data"})
public class UsageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageServiceApplication.class, args);
	}

}
