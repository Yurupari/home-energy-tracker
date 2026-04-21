package com.yurupari.insight_service.service;

import com.yurupari.insight_service.model.dto.InsightDto;
import com.yurupari.insight_service.model.enums.InsightType;

public interface InsightService {

    InsightDto getInsights(Long userId, int days, InsightType insightType);
}
