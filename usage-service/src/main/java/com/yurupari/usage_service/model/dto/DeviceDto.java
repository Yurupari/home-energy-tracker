package com.yurupari.usage_service.model.dto;

import com.yurupari.common_data.model.enums.Status;
import lombok.Builder;

@Builder
public record DeviceDto(
        Long id,
        String name,
        String type,
        String location,
        Long userId,
        Status status,
        Double energyConsumed
) {
}
