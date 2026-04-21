package com.yurupari.insight_service;

import com.yurupari.insight_service.model.PromptProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.yurupari.insight_service", "com.yurupari.common_data"})
@EnableFeignClients
@EnableConfigurationProperties(PromptProperties.class)
public class InsightServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsightServiceApplication.class, args);
	}

}
