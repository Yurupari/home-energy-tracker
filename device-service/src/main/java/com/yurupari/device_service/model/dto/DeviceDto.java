package com.yurupari.device_service.model.dto;

import com.yurupari.common_data.model.enums.Status;

public record DeviceDto(
        Long id,
        String name,
        String type,
        String location,
        Long userId,
        Status status
) {
}
