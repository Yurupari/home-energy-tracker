package com.yurupari.alert_service.messaging.kafka;

import com.yurupari.alert_service.BaseUnitTest;
import com.yurupari.alert_service.service.AlertService;
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

class AlertingConsumerTest extends BaseUnitTest {

    @InjectMocks
    private AlertingConsumer alertingConsumer;

    @Mock
    private AlertService alertService;

    @Test
    void consume() throws IOException {
        var alertingEvent = jsonTestUtils.loadObject(ALERTING_EVENT_JSON, AlertingEvent.class);

        assertDoesNotThrow(() -> alertingConsumer.consume(alertingEvent));

        verify(alertService, times(1)).energyUsageAlertEvent(any());
    }
}