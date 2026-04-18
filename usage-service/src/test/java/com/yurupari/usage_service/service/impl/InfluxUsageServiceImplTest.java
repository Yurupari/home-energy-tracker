package com.yurupari.usage_service.service.impl;

import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.BaseUnitTest;
import com.yurupari.usage_service.repository.impl.InfluxDBRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static com.yurupari.usage_service.constants.TestConstants.ENERGY_USAGE_EVENT_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InfluxUsageServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private InfluxUsageServiceImpl influxUsageService;

    @Mock
    private InfluxDBRepository influxDBRepository;

    @Test
    void energyUsageEvent() throws IOException {
        var energyUsageEvent = jsonTestUtils.loadObject(ENERGY_USAGE_EVENT_JSON, EnergyUsageEvent.class);

        assertDoesNotThrow(() -> influxUsageService.energyUsageEvent(energyUsageEvent));

        verify(influxDBRepository, times(1)).saveUsageEnergy(any());
    }

    @Test
    void getUsageEnergy() {
        var to = Instant.now();
        var from = to.minusSeconds(3600);

        var fluxRecord = new FluxRecord(0);
        fluxRecord.getValues().put("deviceId", "1");
        fluxRecord.getValues().put("_value", 0.050);
        var fluxTable = new FluxTable();
        fluxTable.getRecords().add(fluxRecord);
        when(influxDBRepository.getUsageEnergy(any(), any())).thenReturn(List.of(fluxTable));

        var response = influxUsageService.getUsageEnergy(from, to);

        assertNotNull(response);
    }
}