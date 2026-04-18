package com.yurupari.insight_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.insight_service.model.dto.InsightDto;
import com.yurupari.insight_service.service.InsightService;
import com.yurupari.insight_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class InsightServiceImpl implements InsightService {

    private final UsageService usageService;

    @Override
    public InsightDto getSavingTips(Long userId) {
        return null;
    }

    @Override
    public InsightDto getOverview(Long userId, int days) {
        final var usageDto = usageService.getXDaysUsageForUser(userId, days);

        return null;
    }
}
