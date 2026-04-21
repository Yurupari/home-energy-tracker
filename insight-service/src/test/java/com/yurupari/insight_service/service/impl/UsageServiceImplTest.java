package com.yurupari.insight_service.service.impl;

import com.yurupari.insight_service.BaseUnitTest;
import com.yurupari.insight_service.client.UsageFeignClientV1;
import com.yurupari.insight_service.model.dto.UsageDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.insight_service.constants.TestConstants.USAGE_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UsageServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private UsageServiceImpl usageService;

    @Mock
    private UsageFeignClientV1 usageFeignClientV1;

    @Test
    void getXDaysUsageForUser() throws IOException {
        var usageDto = jsonTestUtils.loadObject(USAGE_DTO_JSON, UsageDto.class);

        when(usageFeignClientV1.getXDaysUsageForUser(any(), any())).thenReturn(usageDto);

        var response = usageService.getXDaysUsageForUser(1L, 3);

        assertNotNull(response);
    }
}