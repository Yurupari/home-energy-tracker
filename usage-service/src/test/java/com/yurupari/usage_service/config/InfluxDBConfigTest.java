package com.yurupari.usage_service.config;

import com.yurupari.usage_service.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class InfluxDBConfigTest extends BaseUnitTest {

    @InjectMocks
    private InfluxDBConfig influxDBConfig;

    @Test
    void influxDBClient() {
        ReflectionTestUtils.setField(influxDBConfig, "influxUrl", "http://localhost:8086");
        ReflectionTestUtils.setField(influxDBConfig, "influxToken", "test-token");
        ReflectionTestUtils.setField(influxDBConfig, "influxOrg", "test-org");

        try (var response = influxDBConfig.influxDBClient()) {
            assertNotNull(response);
        }
    }
}