package com.yurupari.usage_service.scheduler;

import com.yurupari.common_data.kafka.event.AlertingEvent;
import com.yurupari.usage_service.model.DeviceEnergy;
import com.yurupari.usage_service.service.DeviceService;
import com.yurupari.usage_service.service.UsageService;
import com.yurupari.usage_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnergyUsageScheduler {

    private final UsageService usageService;

    private final DeviceService deviceService;

    private final UserService userService;

    private final KafkaTemplate<String, AlertingEvent> kafkaTemplate;

    @Value("${energy-usage.range-time}")
    private Long rangeTime;

    @Scheduled(cron = "*/10 * * * * *")
    public void aggregateDeviceEnergyUsage() {
        final var now = Instant.now();
        final var from = now.minusSeconds(rangeTime);

        var listDeviceEnergy = usageService.getUsageEnergy(from, now);

        var enrichedListDeviceEnergy = enrichWithUserIds(listDeviceEnergy);

        processThresholds(enrichedListDeviceEnergy);
    }

    private List<DeviceEnergy> enrichWithUserIds(List<DeviceEnergy> listDeviceEnergy) {
        return listDeviceEnergy.parallelStream()
                .map(deviceEnergy -> {
                    var deviceDto = deviceService.getDeviceById(deviceEnergy.getDeviceId());

                    Optional.ofNullable(deviceDto).ifPresentOrElse(
                            dto -> {
                                deviceEnergy.setUserId(dto.userId());
                            },
                            () -> log.warn("Device not found: id={}", deviceEnergy.getDeviceId())
                    );

                    return deviceEnergy;
                })
                .filter(d -> Objects.nonNull(d.getUserId()))
                .toList();
    }

    private void processThresholds(List<DeviceEnergy> listDeviceEnergy) {
        Map<Long, List<DeviceEnergy>> userDeviceEnergyMap = listDeviceEnergy.stream()
                .collect(Collectors.groupingBy(DeviceEnergy::getUserId));

        var streamUserDto = userDeviceEnergyMap.keySet().stream()
                .map(userService::getUserById);

        streamUserDto.forEach(user -> {
            final var threshold = user.energyAlertingThreshold();
            final var devices = userDeviceEnergyMap.get(user.id());

            final var totalConsumption = devices.stream()
                    .mapToDouble(DeviceEnergy::getEnergyConsumed)
                    .sum();

            if (totalConsumption > threshold) {
                log.info("User has exceeded the energy threshold: userId={}, total_consumption={}, threshold={}",
                        user.id(), totalConsumption, threshold);

                final var alertingEvent = AlertingEvent.builder()
                        .userId(user.id())
                        .message("Energy consumption threshold exceeded")
                        .threshold(threshold)
                        .energyConsumed(totalConsumption)
                        .email(user.email())
                        .build();

                kafkaTemplate.send("energy-alerts", alertingEvent);
            }
            else {
                log.info("User is within the energy threshold: userId={}, total_consumption={}, threshold={}",
                        user.id(), totalConsumption, threshold);
            }
        });
    }
}
