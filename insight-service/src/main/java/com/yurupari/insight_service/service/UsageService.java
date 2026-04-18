package com.yurupari.insight_service.service;

import com.yurupari.insight_service.model.dto.UsageDto;

public interface UsageService {

    UsageDto getXDaysUsageForUser(Long userId, int days);
}
