package com.yurupari.usage_service.controller.v1;

import com.yurupari.usage_service.BaseUnitTest;
import com.yurupari.usage_service.model.dto.UsageDto;
import com.yurupari.usage_service.service.UsageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.usage_service.constants.TestConstants.USAGE_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class UsageControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private UsageControllerV1 usageControllerV1;

    @Mock
    private UsageService usageService;

    @Test
    void getXDaysUsageForUser() throws IOException {
        var usageDto = jsonTestUtils.loadObject(USAGE_DTO_JSON, UsageDto.class);

        when(usageService.getXDaysUsageForUser(any(), anyInt())).thenReturn(usageDto);

        var response = usageControllerV1.getXDaysUsageForUser(1L, 3);

        assertNotNull(response);
    }
}