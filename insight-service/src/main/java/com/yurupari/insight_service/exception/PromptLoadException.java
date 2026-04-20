package com.yurupari.insight_service.exception;

import com.yurupari.insight_service.model.enums.InsightType;

public class PromptLoadException extends RuntimeException {

    public PromptLoadException(InsightType insightType) {
        super(String.format("Error loading the prompt: type=%s", insightType));
    }
}
