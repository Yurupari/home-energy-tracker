package com.yurupari.usage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.yurupari.usage_service", "com.yurupari.common_data"})
@EnableFeignClients
@EnableScheduling
public class UsageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageServiceApplication.class, args);
	}

}
