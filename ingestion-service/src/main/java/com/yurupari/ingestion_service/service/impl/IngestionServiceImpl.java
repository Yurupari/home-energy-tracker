package com.yurupari.ingestion_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.ingestion_service.model.mapper.EnergyUsageMapper;
import com.yurupari.ingestion_service.model.dto.EnergyUsageDto;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.ingestion_service.service.IngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class IngestionServiceImpl implements IngestionService {

    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    private final EnergyUsageMapper energyUsageMapper;

    @Override
    public void ingestEnergyUsage(EnergyUsageDto energyUsageDto) {
        var energyUsageEvent = energyUsageMapper.toEvent(energyUsageDto);

        kafkaTemplate.send("energy-usage", energyUsageEvent)
                .whenComplete((result, ex) ->
                    Optional.ofNullable(ex).ifPresentOrElse(
                            e -> log.error("Unable to ingest Energy Usage Event: error={}", ex.getMessage()),
                            () -> log.info("Ingested Energy Usage Event: event={}", energyUsageEvent)
                    )
                );
    }
}
