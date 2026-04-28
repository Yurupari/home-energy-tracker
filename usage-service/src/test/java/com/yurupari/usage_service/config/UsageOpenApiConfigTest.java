package com.yurupari.usage_service.config;

import com.yurupari.common_data.config.OpenApiConfig;
import com.yurupari.usage_service.BaseUnitTest;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsageOpenApiConfigTest extends BaseUnitTest {

    @InjectMocks
    private UsageOpenApiConfig usageOpenApiConfig;
    
    @Mock
    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(usageOpenApiConfig, "title", "Usage service");
        ReflectionTestUtils.setField(usageOpenApiConfig, "description", "Usage Service API");
        ReflectionTestUtils.setField(usageOpenApiConfig, "version", "1.0.0");
    }

    @Test
    void usageServiceApiDocs() {
        when(openApiConfig.serviceApiDocs(anyString(), anyString(), anyString())).thenReturn(mock(OpenAPI.class));

        var response = usageOpenApiConfig.usageServiceApiDocs();

        assertNotNull(response);
    }
}