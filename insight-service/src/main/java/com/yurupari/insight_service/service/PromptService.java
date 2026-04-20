package com.yurupari.insight_service.service;

import com.yurupari.insight_service.model.enums.InsightType;

public interface PromptService {

    String getPromptTemplate(InsightType insightType);
}
