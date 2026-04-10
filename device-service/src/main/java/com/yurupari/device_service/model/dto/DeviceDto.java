package com.yurupari.device_service.model.dto;

import com.yurupari.common_data.model.enums.Status;
import com.yurupari.device_service.model.enums.DeviceType;

public record DeviceDto(
        Long id,
        String name,
        DeviceType type,
        String location,
        Long userId,
        Status status
) {
}
