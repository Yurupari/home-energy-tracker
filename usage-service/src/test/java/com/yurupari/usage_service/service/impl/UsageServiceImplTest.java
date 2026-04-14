package com.yurupari.usage_service.service.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static com.yurupari.usage_service.constants.TestConstants.ENERGY_USAGE_EVENT_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsageServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private UsageServiceImpl usageService;

    @Mock
    private InfluxDBClient influxDBClient;

    private EnergyUsageEvent energyUsageEvent;

    @BeforeEach
    void setUp() throws IOException {
        ReflectionTestUtils.setField(usageService, "influxBucket", "test-bucket");
        ReflectionTestUtils.setField(usageService, "influxOrg", "test-org");

        energyUsageEvent = jsonTestUtils.loadObject(ENERGY_USAGE_EVENT_JSON, EnergyUsageEvent.class);
    }

    @Test
    void energyUsageEvent() {
        var writeApiBlocking = mock(WriteApiBlocking.class);
        when(influxDBClient.getWriteApiBlocking()).thenReturn(writeApiBlocking);

        assertDoesNotThrow(() -> usageService.energyUsageEvent(energyUsageEvent));

        var pointCaptor = ArgumentCaptor.forClass(Point.class);
        verify(writeApiBlocking).writePoint(
                eq("test-bucket"),
                eq("test-org"),
                pointCaptor.capture()
        );

        var capturedPoint = pointCaptor.getValue();
        assertNotNull(capturedPoint);
    }

    @Test
    void getUsageEnergy() {
        var to = Instant.now();
        var from = to.minusSeconds(3600);

        var queryApi = mock(QueryApi.class);
        when(influxDBClient.getQueryApi()).thenReturn(queryApi);

        var fluxRecord = new FluxRecord(0);
        fluxRecord.getValues().put("deviceId", "1");
        fluxRecord.getValues().put("_value", 0.050);

        var fluxTable = new FluxTable();
        fluxTable.getRecords().add(fluxRecord);

        when(queryApi.query(anyString(), anyString())).thenReturn(List.of(fluxTable));

        var response = usageService.getUsageEnergy(from, to);

        assertNotNull(response);
    }
}