package com.yurupari.usage_service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.client.DeviceFeignClientV1;
import com.yurupari.usage_service.client.UserFeignClientV1;
import com.yurupari.usage_service.model.dto.DeviceDto;
import com.yurupari.usage_service.model.dto.UserDto;
import com.yurupari.usage_service.scheduler.EnergyUsageScheduler;
import com.yurupari.usage_service.utils.JsonTestUtils;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.yurupari.usage_service.constants.TestConstants.DEVICE_DTO_JSON;
import static com.yurupari.usage_service.constants.TestConstants.ENERGY_USAGE_EVENT_JSON;
import static com.yurupari.usage_service.constants.TestConstants.USER_DTO_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(partitions = 1, topics = {"energy-usage", "energy-alerts"})
class UsageServiceApplicationTests {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry registry;

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private JsonTestUtils jsonTestUtils;

	@MockitoBean
	private DeviceFeignClientV1 deviceFeignClientV1;

	@MockitoBean
	private UserFeignClientV1 userFeignClientV1;

	@MockitoBean
	private InfluxDBClient influxDBClient;

	@MockitoSpyBean
	private EnergyUsageScheduler energyUsageScheduler;

	private WriteApiBlocking writeApiBlocking;

	@BeforeEach
	void setUp() {
		writeApiBlocking = mock(WriteApiBlocking.class);

		when(influxDBClient.getWriteApiBlocking()).thenReturn(writeApiBlocking);

		for (MessageListenerContainer container : registry.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
		}
	}

	@Test
	void contextLoads() {
	}

	@Test
	void consumeEnergyUsageEvent() throws IOException {
		var energyUsageEvent = jsonTestUtils.loadObject(ENERGY_USAGE_EVENT_JSON, EnergyUsageEvent.class);

		kafkaTemplate.send("energy-usage", energyUsageEvent);

		Awaitility.await()
				.atMost(Duration.ofSeconds(5))
				.untilAsserted(() -> {
					verify(writeApiBlocking).writePoint(
							eq("test-bucket"),
							eq("test-org"),
							any(Point.class)
					);
				});
	}

	@Test
	void processEnergyUsageAndSendAlerts() throws IOException {
		var queryApi = mock(QueryApi.class);
		when(influxDBClient.getQueryApi()).thenReturn(queryApi);

		var fluxRecord = new FluxRecord(0);
		fluxRecord.getValues().put("deviceId", "1");
		fluxRecord.getValues().put("_value", 0.050);

		var fluxTable = new FluxTable();
		fluxTable.getRecords().add(fluxRecord);

		when(queryApi.query(anyString(), anyString())).thenReturn(List.of(fluxTable));

		var deviceDto = jsonTestUtils.loadObject(DEVICE_DTO_JSON, DeviceDto.class);
		when(deviceFeignClientV1.getDeviceById(101L)).thenReturn(deviceDto);

		var userDto = jsonTestUtils.loadObject(USER_DTO_JSON, UserDto.class);
		when(userFeignClientV1.getUserById(1L)).thenReturn(userDto);

		Awaitility.await()
				.atMost(Duration.ofSeconds(15))
				.untilAsserted(() -> {
					verify(energyUsageScheduler, atLeastOnce()).aggregateDeviceEnergyUsage();
				});
	}
}
