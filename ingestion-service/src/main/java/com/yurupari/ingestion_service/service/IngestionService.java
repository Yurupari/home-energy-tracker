package com.yurupari.ingestion_service.service;

import com.yurupari.ingestion_service.model.dto.EnergyUsageDto;

public interface IngestionService {
    void ingestEnergyUsage(EnergyUsageDto energyUsageDto);
}
