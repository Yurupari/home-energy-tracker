package com.yurupari.usage_service.service;

import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.model.DeviceEnergy;
import com.yurupari.usage_service.model.dto.UsageDto;

import java.time.Instant;
import java.util.List;

public interface UsageService {

    void energyUsageEvent(EnergyUsageEvent energyUsageEvent);
    List<DeviceEnergy> getUsageEnergy(Instant from, Instant to);
    UsageDto getXDaysUsageForUser(Long userId, int days);
}
