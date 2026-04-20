package com.yurupari.insight_service;

import com.yurupari.insight_service.model.PromptProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"com.yurupari.insight_service", "com.yurupari.common_data"})
@EnableConfigurationProperties(PromptProperties.class)
public class InsightServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsightServiceApplication.class, args);
	}

}
