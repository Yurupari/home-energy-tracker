package com.yurupari.alert_service;

import com.yurupari.alert_service.client.UserFeignClientV1;
import com.yurupari.alert_service.model.dto.UserDto;
import com.yurupari.alert_service.repository.AlertRepository;
import com.yurupari.alert_service.utils.JsonTestUtils;
import com.yurupari.common_data.kafka.event.AlertingEvent;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.time.Duration;

import static com.yurupari.alert_service.constants.TestConstants.ALERTING_EVENT_JSON;
import static com.yurupari.alert_service.constants.TestConstants.USER_WITH_ALERTING_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/sql/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@EmbeddedKafka(partitions = 1, topics = {"energy-alerts"})
class AlertServiceApplicationTests {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private AlertRepository alertRepository;

	@Autowired
	private JsonTestUtils jsonTestUtils;

	@MockitoBean
	private UserFeignClientV1 userFeignClientV1;

	@MockitoBean
	private JavaMailSender javaMailSender;

	@Test
	void contextLoads() {
	}

	@Test
	void consumeAlertingEvent_EmailSent() throws IOException {
		var alertingEvent = jsonTestUtils.loadObject(ALERTING_EVENT_JSON, AlertingEvent.class);
		var userDto = jsonTestUtils.loadObject(USER_WITH_ALERTING_JSON, UserDto.class);

		when(userFeignClientV1.getUserById(any())).thenReturn(userDto);

		kafkaTemplate.send("energy-alerts", alertingEvent);

		Awaitility.await()
				.atMost(Duration.ofSeconds(10))
				.untilAsserted(() -> {
					verify(javaMailSender).send(any(SimpleMailMessage.class));

					var alerts = alertRepository.findAll();
					assertEquals(1, alerts.size());
					assertEquals(alertingEvent.userId(), alerts.getFirst().getUserId());
					assertTrue(alerts.getFirst().getSent());
				});
	}
}
