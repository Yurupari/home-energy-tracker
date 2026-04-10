package com.yurupari.ingestion_service.model.mapper;

import com.yurupari.common_data.config.CentralMapperConfig;
import com.yurupari.ingestion_service.model.dto.EnergyUsageDto;
import com.yurupari.kafka.event.EnergyUsageEvent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        config = CentralMapperConfig.class
)
public interface EnergyUsageMapper {

    EnergyUsageEvent toEvent(EnergyUsageDto energyUsageDto);
}
