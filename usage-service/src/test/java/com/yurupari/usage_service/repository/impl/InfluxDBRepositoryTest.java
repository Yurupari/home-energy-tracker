package com.yurupari.usage_service.repository.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static com.yurupari.usage_service.constants.TestConstants.ENERGY_USAGE_EVENT_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InfluxDBRepositoryTest extends BaseUnitTest {

    @InjectMocks
    private InfluxDBRepository influxDBRepository;

    @Mock
    private InfluxDBClient influxDBClient;

    private EnergyUsageEvent energyUsageEvent;

    @BeforeEach
    void setUp() throws IOException {
        ReflectionTestUtils.setField(influxDBRepository, "influxBucket", "test-bucket");
        ReflectionTestUtils.setField(influxDBRepository, "influxOrg", "test-org");

        energyUsageEvent = jsonTestUtils.loadObject(ENERGY_USAGE_EVENT_JSON, EnergyUsageEvent.class);
    }

    @Test
    void saveUsageEnergy() {
        var writeApiBlocking = mock(WriteApiBlocking.class);
        when(influxDBClient.getWriteApiBlocking()).thenReturn(writeApiBlocking);

        assertDoesNotThrow(() -> influxDBRepository.saveUsageEnergy(energyUsageEvent));

        verify(writeApiBlocking, times(1)).writePoint(anyString(), anyString(), any());
    }

    @Test
    void saveUsageEnergy_UnexpectedError() {
        var writeApiBlocking = mock(WriteApiBlocking.class);
        when(influxDBClient.getWriteApiBlocking()).thenReturn(writeApiBlocking);

        doThrow(new RuntimeException("Error occurred with InfluxDB"))
                .when(writeApiBlocking)
                        .writePoint(anyString(), anyString(), any());

        assertDoesNotThrow(() -> influxDBRepository.saveUsageEnergy(energyUsageEvent));
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

        var response = influxDBRepository.getUsageEnergy(from, to);

        assertNotNull(response);
    }

    @Test
    void getUsageEnergy_ErrorWithInflux() {
        var to = Instant.now();
        var from = to.minusSeconds(3600);

        var queryApi = mock(QueryApi.class);
        when(influxDBClient.getQueryApi()).thenReturn(queryApi);

        when(queryApi.query(anyString(), anyString())).thenThrow(new RuntimeException("Error occurred with InfluxDB"));

        var response = influxDBRepository.getUsageEnergy(from, to);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void getUsageEnergy_Devices() {
        var devices = List.of("1");
        var to = Instant.now();
        var from = to.minusSeconds((long) 3 * 24 * 3600);

        var queryApi = mock(QueryApi.class);
        when(influxDBClient.getQueryApi()).thenReturn(queryApi);

        var fluxRecord = new FluxRecord(0);
        fluxRecord.getValues().put("deviceId", "1");
        fluxRecord.getValues().put("_value", 0.050);

        var fluxTable = new FluxTable();
        fluxTable.getRecords().add(fluxRecord);

        when(queryApi.query(anyString(), anyString())).thenReturn(List.of(fluxTable));

        var response = influxDBRepository.getUsageEnergy(devices, from, to);

        assertNotNull(response);
    }

    @Test
    void getUsageEnergy_Devices_ErrorWithInflux() {
        var devices = List.of("1");
        var to = Instant.now();
        var from = to.minusSeconds((long) 3 * 24 * 3600);

        var queryApi = mock(QueryApi.class);
        when(influxDBClient.getQueryApi()).thenReturn(queryApi);

        when(queryApi.query(anyString(), anyString())).thenThrow(new RuntimeException("Error occurred with InfluxDB"));

        var response = influxDBRepository.getUsageEnergy(devices, from, to);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }
}