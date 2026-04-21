package com.yurupari.insight_service.model.dto;

import lombok.Builder;

@Builder
public record InsightDto(
        Long userId,
        String tips,
        Double energyUsage
) {
}
