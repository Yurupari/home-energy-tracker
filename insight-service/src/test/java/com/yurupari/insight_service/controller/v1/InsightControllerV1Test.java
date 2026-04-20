package com.yurupari.insight_service.controller.v1;

import com.yurupari.insight_service.BaseUnitTest;
import com.yurupari.insight_service.model.dto.InsightDto;
import com.yurupari.insight_service.service.InsightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.insight_service.constants.TestConstants.INSIGHT_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class InsightControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private InsightControllerV1 insightControllerV1;

    @Mock
    private InsightService insightService;

    private InsightDto insightDto;

    @BeforeEach
    void setUp() throws IOException {
        insightDto = jsonTestUtils.loadObject(INSIGHT_DTO_JSON, InsightDto.class);
    }

    @Test
    void getSavingTips() {
        when(insightService.getInsights(any(), anyInt(), any())).thenReturn(insightDto);

        var response = insightControllerV1.getSavingTips(1L, 3);

        assertNotNull(response);
    }

    @Test
    void getOverview() {
        when(insightService.getInsights(any(), anyInt(), any())).thenReturn(insightDto);

        var response = insightControllerV1.getOverview(1L, 3);

        assertNotNull(response);
    }
}