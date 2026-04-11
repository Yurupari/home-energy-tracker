package com.yurupari.device_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yurupari.device_service", "com.yurupari.common_data"})
public class DeviceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceServiceApplication.class, args);
	}

}
