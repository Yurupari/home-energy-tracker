package com.yurupari.insight_service.service;

import com.yurupari.insight_service.model.dto.InsightDto;

public interface InsightService {

    InsightDto getSavingTips(Long userId, int days);
    InsightDto getOverview(Long userId, int days);
}
