package com.yurupari.alert_service.messaging.kafka;

import com.yurupari.alert_service.service.AlertService;
import com.yurupari.common_data.kafka.event.AlertingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertingConsumer {

    private final AlertService alertService;

    @KafkaListener(topics = "energy-alerts", groupId = "alert-service")
    public void consume(AlertingEvent alertingEvent) {
        log.info("Received alert: event={}", alertingEvent);
        alertService.energyUsageAlertEvent(alertingEvent);
    }
}
