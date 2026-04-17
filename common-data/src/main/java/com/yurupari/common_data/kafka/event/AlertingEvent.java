package com.yurupari.common_data.kafka.event;

import com.yurupari.common_data.model.DeviceConsumption;
import lombok.Builder;

import java.util.List;

@Builder
public record AlertingEvent(
        Long userId,
        List<DeviceConsumption> devices,
        String message,
        Double threshold,
        Double energyConsumed,
        String email
) {
}
