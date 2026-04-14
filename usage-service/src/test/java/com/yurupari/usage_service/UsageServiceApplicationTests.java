package com.yurupari.usage_service;

import com.yurupari.usage_service.client.DeviceFeignClient;
import com.yurupari.usage_service.client.UserFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@EmbeddedKafka(partitions = 1)
class UsageServiceApplicationTests {

	@MockitoBean
	private DeviceFeignClient deviceFeignClient;

	@MockitoBean
	private UserFeignClient userFeignClient;

	@Test
	void contextLoads() {
	}

}
