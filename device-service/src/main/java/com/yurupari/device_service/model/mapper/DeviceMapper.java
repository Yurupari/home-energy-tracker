package com.yurupari.device_service.model.mapper;

import com.yurupari.common_data.config.CentralMapperConfig;
import com.yurupari.device_service.model.dto.DeviceDto;
import com.yurupari.device_service.model.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        config = CentralMapperConfig.class
)
public interface DeviceMapper {

    DeviceDto toDto(Device device);

    @Mapping(target = "status", defaultValue = "ACTIVE")
    Device toEntity(DeviceDto deviceDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(DeviceDto deviceDto, @MappingTarget Device device);
}
