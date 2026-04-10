package com.yurupari.ingestion_service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record EnergyUsageDto(
        Long deviceId,
        Double energyConsumed,
        @JsonFormat(shape = JsonFormat.Shape.STRING) Instant timestamp
        ) {
}
