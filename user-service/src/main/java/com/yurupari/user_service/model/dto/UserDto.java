package com.yurupari.user_service.model.dto;

import com.yurupari.user_service.model.enums.Status;

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
