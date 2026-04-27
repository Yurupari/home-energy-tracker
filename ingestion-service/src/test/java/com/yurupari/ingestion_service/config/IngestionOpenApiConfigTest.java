package com.yurupari.ingestion_service.config;

import com.yurupari.common_data.config.OpenApiConfig;
import com.yurupari.ingestion_service.BaseUnitTest;
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

class IngestionOpenApiConfigTest extends BaseUnitTest {

    @InjectMocks
    private IngestionOpenApiConfig ingestionOpenApiConfig;
    
    @Mock
    private OpenApiConfig openApiConfig;
    
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ingestionOpenApiConfig, "title", "Ingestion service");
        ReflectionTestUtils.setField(ingestionOpenApiConfig, "description", "Ingestion Service API");
        ReflectionTestUtils.setField(ingestionOpenApiConfig, "version", "1.0.0");
    }

    @Test
    void ingestionServiceApiDocs() {
        when(openApiConfig.serviceApiDocs(anyString(), anyString(), anyString())).thenReturn(mock(OpenAPI.class));

        var response = ingestionOpenApiConfig.ingestionServiceApiDocs();

        assertNotNull(response);
    }
}