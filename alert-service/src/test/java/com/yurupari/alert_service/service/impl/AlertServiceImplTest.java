package com.yurupari.alert_service.service.impl;

import com.yurupari.alert_service.BaseUnitTest;
import com.yurupari.alert_service.repository.AlertRepository;
import com.yurupari.alert_service.service.EmailService;
import com.yurupari.common_data.kafka.event.AlertingEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.alert_service.constants.TestConstants.ALERTING_EVENT_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlertServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private AlertServiceImpl alertService;

    @Mock
    private EmailService emailService;

    @Mock
    private AlertRepository alertRepository;

    @Test
    void energyUsageAlertEvent() throws IOException {
        var alertingEvent = jsonTestUtils.loadObject(ALERTING_EVENT_JSON, AlertingEvent.class);

        when(emailService.sendEmail(any(), any(), any())).thenReturn(true);

        assertDoesNotThrow(() -> alertService.energyUsageAlertEvent(alertingEvent));

        verify(alertRepository, times(1)).saveAndFlush(any());
    }
}