package com.yurupari.insight_service.service.impl;

import com.yurupari.insight_service.model.dto.DeviceDto;
import com.yurupari.insight_service.model.dto.InsightDto;
import com.yurupari.insight_service.model.enums.InsightType;
import com.yurupari.insight_service.service.InsightService;
import com.yurupari.insight_service.service.LLMChatService;
import com.yurupari.insight_service.service.PromptService;
import com.yurupari.insight_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsightServiceImpl implements InsightService {

    private final UsageService usageService;

    private final PromptService promptService;

    private final LLMChatService llmChatService;

    @Override
    public InsightDto getInsights(Long userId, int days, InsightType insightType) {
        log.info("Getting insights: userId={}, days={}, insightType={}",
                userId, days, insightType);

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
