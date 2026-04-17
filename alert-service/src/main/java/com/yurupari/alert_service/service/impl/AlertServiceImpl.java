package com.yurupari.alert_service.service.impl;

import com.yurupari.alert_service.model.entity.Alert;
import com.yurupari.alert_service.repository.AlertRepository;
import com.yurupari.alert_service.service.AlertService;
import com.yurupari.alert_service.service.EmailService;
import com.yurupari.alert_service.service.UserService;
import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.common_data.kafka.event.AlertingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Loggable
public class AlertServiceImpl implements AlertService {

    private final EmailService emailService;

    private final UserService userService;

    private final AlertRepository alertRepository;

    @Override
    public void energyUsageAlertEvent(AlertingEvent alertingEvent) {
        final var subject = "Energy Usage Alert for User "
                + alertingEvent.userId();
        final var message = buildMessage(alertingEvent);

        var userDto = userService.getUserById(alertingEvent.userId());

        final var alert = Alert.builder()
                .sent(userDto.alerting()
                        && emailService.sendEmail(alertingEvent.email(), subject, message))
                .userId(alertingEvent.userId())
                .build();

        alertRepository.saveAndFlush(alert);
    }

    private String buildMessage(AlertingEvent alertingEvent) {
        var devices = alertingEvent.devices().stream()
                .map(d -> String.format(" - %s\n   Location: %s\n   Energy Consumed: %s\n",
                        d.name(), d.location(), d.energyConsumed()))
                .collect(Collectors.joining());

        return String.format("Alert: %s\nThreshold: %s\nEnergy Consumed: %s\nDevices:\n%s",
                alertingEvent.message(), alertingEvent.threshold(), alertingEvent.energyConsumed(), devices);
    }
}
