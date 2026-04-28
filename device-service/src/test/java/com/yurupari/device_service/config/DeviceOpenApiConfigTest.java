package com.yurupari.device_service.config;

import com.yurupari.common_data.config.OpenApiConfig;
import com.yurupari.device_service.BaseUnitTest;
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

class DeviceOpenApiConfigTest extends BaseUnitTest {

    @InjectMocks
    private DeviceOpenApiConfig deviceOpenApiConfig;

    @Mock
    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(deviceOpenApiConfig, "title", "Device service");
        ReflectionTestUtils.setField(deviceOpenApiConfig, "description", "Device Service API");
        ReflectionTestUtils.setField(deviceOpenApiConfig, "version", "1.0.0");
    }

    @Test
    void deviceServiceApiDocs() {
        when(openApiConfig.serviceApiDocs(anyString(), anyString(), anyString())).thenReturn(mock(OpenAPI.class));

        var response = deviceOpenApiConfig.deviceServiceApiDocs();

        assertNotNull(response);
    }
}