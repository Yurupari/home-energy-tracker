package com.yurupari.insight_service.service.impl;

import com.yurupari.insight_service.BaseUnitTest;
import com.yurupari.insight_service.model.dto.UsageDto;
import com.yurupari.insight_service.model.enums.InsightType;
import com.yurupari.insight_service.service.LLMChatService;
import com.yurupari.insight_service.service.PromptService;
import com.yurupari.insight_service.service.UsageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.insight_service.constants.TestConstants.USAGE_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class InsightServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private InsightServiceImpl insightService;

    @Mock
    private UsageService usageService;

    @Mock
    private PromptService promptService;

    @Mock
    private LLMChatService llmChatService;

    @Test
    void getInsights() throws IOException {
        var usageDto = jsonTestUtils.loadObject(USAGE_DTO_JSON, UsageDto.class);

        when(usageService.getXDaysUsageForUser(any(), anyInt())).thenReturn(usageDto);
        when(promptService.getPromptTemplate(any())).thenReturn("TEST TEMPLATE: DAYS={{days}}, DEVICES={{devices}}");
        when(llmChatService.getInsights(anyString())).thenReturn("TEST_INSIGHT");

        var responseSavingTips = insightService.getInsights(1L, 3, InsightType.SAVING_TIPS);
        var responseOverview = insightService.getInsights(1L, 3, InsightType.OVERVIEW);

        assertNotNull(responseSavingTips);
        assertNotNull(responseOverview);
    }
}