package com.yurupari.usage_service.service;

import com.yurupari.common_data.kafka.event.EnergyUsageEvent;

public interface UsageService {

    void energyUsageEvent(EnergyUsageEvent energyUsageEvent);
}
