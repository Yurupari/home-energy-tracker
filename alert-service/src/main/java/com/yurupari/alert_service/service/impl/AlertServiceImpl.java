package com.yurupari.alert_service.service.impl;

import com.yurupari.alert_service.model.entity.Alert;
import com.yurupari.alert_service.repository.AlertRepository;
import com.yurupari.alert_service.service.AlertService;
import com.yurupari.alert_service.service.EmailService;
import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.common_data.kafka.event.AlertingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class AlertServiceImpl implements AlertService {

    private final EmailService emailService;

    private final AlertRepository alertRepository;

    @Override
    public void energyUsageAlertEvent(AlertingEvent alertingEvent) {
        final var subject = "Energy Usage Alert for User "
                + alertingEvent.userId();
        final var message = String.format("Alert: %s\nThreshold: %s\nEnergy Consumed: %s",
                alertingEvent.message(), alertingEvent.threshold(), alertingEvent.energyConsumed());

        final var alert = Alert.builder()
                .sent(emailService.sendEmail(alertingEvent.email(), subject, message))
                .userId(alertingEvent.userId())
                .build();

        alertRepository.saveAndFlush(alert);
    }
}
