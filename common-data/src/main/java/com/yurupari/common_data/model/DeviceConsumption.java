package com.yurupari.common_data.model;

import lombok.Builder;

@Builder
public record DeviceConsumption(
        String name,
        String location,
        Double energyConsumed
) {
}
