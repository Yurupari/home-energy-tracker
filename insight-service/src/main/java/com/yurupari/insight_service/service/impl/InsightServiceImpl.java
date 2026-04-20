package com.yurupari.insight_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.insight_service.model.dto.DeviceDto;
import com.yurupari.insight_service.model.dto.InsightDto;
import com.yurupari.insight_service.model.enums.InsightType;
import com.yurupari.insight_service.service.InsightService;
import com.yurupari.insight_service.service.LLMChatService;
import com.yurupari.insight_service.service.PromptService;
import com.yurupari.insight_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class InsightServiceImpl implements InsightService {

    private final UsageService usageService;

    private final PromptService promptService;

    private final LLMChatService llmChatService;

    @Override
    public InsightDto getInsights(Long userId, int days, InsightType insightType) {
        final var usageDto = usageService.getXDaysUsageForUser(userId, days);

        var totalUsage = usageDto.devices().stream()
                .mapToDouble(DeviceDto::energyConsumed)
                .sum();

        var template = promptService.getPromptTemplate(insightType);
        var response = llmChatService.getInsights(template.formatted(
                days,
                usageDto.devices()
        ));

        return InsightDto.builder()
                .userId(userId)
                .tips(response)
                .energyUsage(totalUsage)
                .build();
    }
}
