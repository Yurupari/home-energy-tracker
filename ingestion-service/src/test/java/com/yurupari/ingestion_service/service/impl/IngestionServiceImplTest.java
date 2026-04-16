package com.yurupari.ingestion_service.service.impl;

import com.yurupari.ingestion_service.BaseUnitTest;
import com.yurupari.ingestion_service.model.dto.EnergyUsageDto;
import com.yurupari.ingestion_service.model.mapper.EnergyUsageMapperImpl;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class IngestionServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private IngestionServiceImpl ingestionService;

    @Mock
    private KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    @Spy
    private EnergyUsageMapperImpl energyUsageMapper = new EnergyUsageMapperImpl();

    @Test
    void ingestEnergyUsage_Success() throws IOException {
        var energyUsageDto = jsonTestUtils.loadObject("model/energy_usage.json", EnergyUsageDto.class);

        when(kafkaTemplate.send(anyString(), any()))
                .thenReturn(CompletableFuture.completedFuture(null));

        assertDoesNotThrow(() -> ingestionService.ingestEnergyUsage(energyUsageDto));
    }

    @Test
    void ingestEnergyUsage_MessageNotSent() throws IOException {
        var energyUsageDto = jsonTestUtils.loadObject("model/energy_usage.json", EnergyUsageDto.class);

        when(kafkaTemplate.send(anyString(), any()))
                .thenReturn(CompletableFuture.failedFuture(new Exception("Error sending message")));

        assertDoesNotThrow(() -> ingestionService.ingestEnergyUsage(energyUsageDto));
    }
}