package com.yurupari.usage_service.repository;

import com.yurupari.common_data.kafka.event.EnergyUsageEvent;

import java.time.Instant;
import java.util.List;

public interface TimeSeriesRepository {

    void saveUsageEnergy(EnergyUsageEvent energyUsageEvent);
    List<?> getUsageEnergy(Instant from, Instant to);
    List<?> getUsageEnergy(List<String> listDeviceId, Instant from, Instant to);
}
