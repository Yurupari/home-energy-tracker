package com.yurupari.usage_service.messaging.kafka;

import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnergyUsageConsumer {

    private final UsageService usageService;

    @KafkaListener(topics = "energy-usage", groupId = "usage-service")
    public void consume(EnergyUsageEvent energyUsageEvent) {
        log.info("Received energy usage event: {}", energyUsageEvent);
        usageService.energyUsageEvent(energyUsageEvent);
    }
}
