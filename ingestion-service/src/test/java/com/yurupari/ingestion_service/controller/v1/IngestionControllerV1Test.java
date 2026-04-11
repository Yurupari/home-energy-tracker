package com.yurupari.ingestion_service.controller.v1;

import com.yurupari.ingestion_service.BaseUnitTest;
import com.yurupari.ingestion_service.model.dto.EnergyUsageDto;
import com.yurupari.ingestion_service.service.IngestionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class IngestionControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private IngestionControllerV1 ingestionControllerV1;

    @Mock
    private IngestionService ingestionService;

    @Test
    void ingestData() throws IOException {
        var energyUsageDto = jsonTestUtils.loadObject("model/energy_usage.json", EnergyUsageDto.class);

        assertDoesNotThrow(() -> ingestionControllerV1.ingestData(energyUsageDto));

        verify(ingestionService, times(1)).ingestEnergyUsage(any());
    }
}