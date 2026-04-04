package com.yurupari.user_service.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CentralMapperConfig {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void ignoreAuditFields(Object source, @MappingTarget Object target);
}
