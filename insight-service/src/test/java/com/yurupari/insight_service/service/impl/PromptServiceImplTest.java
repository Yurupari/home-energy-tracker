package com.yurupari.insight_service.service.impl;

import com.yurupari.insight_service.BaseUnitTest;
import com.yurupari.insight_service.exception.PromptLoadException;
import com.yurupari.insight_service.model.PromptProperties;
import com.yurupari.insight_service.model.enums.InsightType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PromptServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private PromptServiceImpl promptService;

    @Mock
    private PromptProperties promptProperties;

    @Test
    void getPromptTemplate() {
        Map<String, String> files = Map.of(
                "saving-tips", "saving-tips.txt",
                "overview", "overview.txt"
        );

        when(promptProperties.basePath()).thenReturn("/templates/prompts/");
        when(promptProperties.files()).thenReturn(files);

        var responseSavingTips = promptService.getPromptTemplate(InsightType.SAVING_TIPS);
        var responseOverview = promptService.getPromptTemplate(InsightType.OVERVIEW);

        assertNotNull(responseSavingTips);
        assertNotNull(responseOverview);
    }

    @Test
    void getPromptTemplate_IOException() {
        when(promptProperties.files()).thenReturn(Map.of("overview", "non-existent.txt"));

        assertThrows(PromptLoadException.class, () -> promptService.getPromptTemplate(InsightType.SAVING_TIPS));
    }
}