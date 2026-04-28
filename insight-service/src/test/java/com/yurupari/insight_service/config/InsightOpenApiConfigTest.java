package com.yurupari.insight_service.config;

import com.yurupari.common_data.config.OpenApiConfig;
import com.yurupari.insight_service.BaseUnitTest;
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

class InsightOpenApiConfigTest extends BaseUnitTest {

    @InjectMocks
    private InsightOpenApiConfig insightOpenApiConfig;

    @Mock
    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(insightOpenApiConfig, "title", "Insight service");
        ReflectionTestUtils.setField(insightOpenApiConfig, "description", "Insight Service API");
        ReflectionTestUtils.setField(insightOpenApiConfig, "version", "1.0.0");
    }

    @Test
    void insightServiceApiDocs() {
        when(openApiConfig.serviceApiDocs(anyString(), anyString(), anyString())).thenReturn(mock(OpenAPI.class));

        var response = insightOpenApiConfig.insightServiceApiDocs();

        assertNotNull(response);
    }
}