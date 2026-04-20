package com.yurupari.insight_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.insight_service.model.dto.DeviceDto;
import com.yurupari.insight_service.model.dto.InsightDto;
import com.yurupari.insight_service.model.enums.InsightType;
import com.yurupari.insight_service.service.InsightService;
import com.yurupari.insight_service.service.LLMChatService;
import com.yurupari.insight_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Loggable
public class InsightServiceImpl implements InsightService {

    private final UsageService usageService;

    private final LLMChatService llmChatService;

    @Override
    public InsightDto getInsights(Long userId, int days, InsightType insightType) {
        final var usageDto = usageService.getXDaysUsageForUser(userId, days);

        var totalUsage = usageDto.devices().stream()
                .mapToDouble(DeviceDto::energyConsumed)
                .sum();

        String prompt = "";
        switch (insightType) {
            case SAVING_TIPS -> prompt = buildSavingTipsPrompt(usageDto.devices(), days);
            case OVERVIEW -> prompt = buildOverviewPrompt(usageDto.devices(), days);
        }
        var response = llmChatService.getInsights(prompt);

        return InsightDto.builder()
                .userId(userId)
                .tips(response)
                .energyUsage(totalUsage)
                .build();
    }

    private String buildSavingTipsPrompt(List<DeviceDto> devices, int days) {
        return new StringBuilder()
                .append("Analyse the following energy usage data and provide "
                        + "saving tips with actionable insights.")
                .append("This data is the aggregate data for the past ")
                .append(days)
                .append(" days.")
                .append("Usage Data: \n")
                .append(devices)
                .toString();
    }

    private String buildOverviewPrompt(List<DeviceDto> devices, int days) {
        return new StringBuilder()
                .append("Analyse the following energy usage data and provide a "
                        + "concise overview with actionable insights.")
                .append("This data is the aggregate data for the past ")
                .append(days)
                .append(" days.")
                .append("Usage Data: \n")
                .append(devices)
                .toString();
    }
}
