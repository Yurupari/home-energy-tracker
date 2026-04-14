package com.yurupari.common_data.kafka.event;

import lombok.Builder;

@Builder
public record AlertingEvent(
        Long userId,
        String message,
        Double threshold,
        Double energyConsumed,
        String email
) {
}
