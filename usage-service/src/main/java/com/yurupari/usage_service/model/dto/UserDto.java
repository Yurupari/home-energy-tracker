package com.yurupari.usage_service.model.dto;

import com.yurupari.common_data.model.enums.Status;

public record UserDto(
        Long id,
        String name,
        String surname,
        String email,
        String address,
        Boolean alerting,
        Double energyAlertingThreshold,
        Status status
) {
}
