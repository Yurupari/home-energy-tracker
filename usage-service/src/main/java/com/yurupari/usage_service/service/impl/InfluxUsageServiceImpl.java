package com.yurupari.usage_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.model.DeviceEnergy;
import com.yurupari.usage_service.repository.impl.InfluxDBRepository;
import com.yurupari.usage_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Loggable
public class InfluxUsageServiceImpl implements UsageService {

    private final InfluxDBRepository influxDBRepository;

    @Override
    public void energyUsageEvent(EnergyUsageEvent energyUsageEvent) {
        influxDBRepository.saveUsageEnergy(energyUsageEvent);
    }

    @Override
    public List<DeviceEnergy> getUsageEnergy(Instant from, Instant to) {
        var fluxTables = influxDBRepository.getUsageEnergy(from, to);

        List<DeviceEnergy> deviceEnergies = new ArrayList<>();

        for (var table : fluxTables) {
            for (var record : table.getRecords()) {
                var deviceId = (String) record.getValueByKey("deviceId");
                var energyConsumed = record.getValueByKey("_value") instanceof Number ?
                        ((Number) record.getValueByKey("_value")).doubleValue() : 0.0;

                deviceEnergies.add(
                        DeviceEnergy.builder()
                                .deviceId(Long.valueOf(deviceId))
                                .energyConsumed(energyConsumed)
                                .build()
                );
            }
        }

        return deviceEnergies;
    }
}
