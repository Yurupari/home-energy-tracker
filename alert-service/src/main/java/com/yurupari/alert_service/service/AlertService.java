package com.yurupari.alert_service.service;

import com.yurupari.common_data.kafka.event.AlertingEvent;

public interface AlertService {

    void energyUsageAlertEvent(AlertingEvent alertingEvent);
}
