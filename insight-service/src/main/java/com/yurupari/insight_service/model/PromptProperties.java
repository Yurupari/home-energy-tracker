package com.yurupari.insight_service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "prompts")
public record PromptProperties(
        String basePath,
        Map<String, String> files
) {
}
