package com.yurupari.usage_service.service.impl;

import com.influxdb.query.FluxRecord;
import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.model.DeviceEnergy;
import com.yurupari.usage_service.model.dto.DeviceDto;
import com.yurupari.usage_service.model.dto.UsageDto;
import com.yurupari.usage_service.repository.impl.InfluxDBRepository;
import com.yurupari.usage_service.service.DeviceService;
import com.yurupari.usage_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class InfluxUsageServiceImpl implements UsageService {

    private final InfluxDBRepository influxDBRepository;

    private final DeviceService deviceService;

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
                var deviceId = getDeviceId(record);
                var energyConsumed = getEnergyConsumed(record);

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

    @Override
    public UsageDto getXDaysUsageForUser(Long userId, int days) {
        final var devices = deviceService.getAllDevicesForUser(userId);

        if (devices.isEmpty()) {
            return UsageDto.builder()
                    .userId(userId)
                    .devices(devices)
                    .build();
        }

        var listDeviceIdString = devices.stream()
                .map(DeviceDto::id)
                .map(String::valueOf)
                .toList();

        final var now = Instant.now();
        final var start = now.minusSeconds((long) days * 24 * 3600);

        final var fluxTables = influxDBRepository.getUsageEnergy(
                listDeviceIdString,
                start,
                now);

        if (fluxTables.isEmpty()) {
            return UsageDto.builder()
                    .userId(userId)
                    .devices(List.of())
                    .build();
        }

        final Map<Long, Double> aggregatedMap = new HashMap<>();
        for (var table : fluxTables) {
            for (var record : table.getRecords()) {
                var deviceIdStr = getDeviceId(record);
                var energyConsumed = getEnergyConsumed(record);

                try {
                    var deviceId = Long.valueOf(deviceIdStr);
                    aggregatedMap.put(deviceId, aggregatedMap.getOrDefault(deviceId, 0.0) + energyConsumed);
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse from flux record: deviceId={}", deviceIdStr);
                }
            }
        }

        final var resultDevices = devices.stream()
                .map(d -> DeviceDto.builder()
                        .id(d.id())
                        .name(d.name())
                        .type(d.type())
                        .location(d.location())
                        .userId(d.userId())
                        .energyConsumed(aggregatedMap.getOrDefault(d.id(), 0.0))
                        .build())
                .toList();

        return UsageDto.builder()
                .userId(userId)
                .devices(resultDevices)
                .build();
    }

    private String getDeviceId(FluxRecord record) {
        return Optional.ofNullable(record.getValueByKey("deviceId"))
                .map(Object::toString)
                .orElse(null);
    }

    private Double getEnergyConsumed(FluxRecord record) {
        return Optional.ofNullable(record.getValueByKey("_value"))
                .filter(v -> v instanceof Number)
                .map(v -> ((Number) v).doubleValue())
                .orElse(0.0);
    }
}
