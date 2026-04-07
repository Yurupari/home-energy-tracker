package com.yurupari.device_service.model.entity;

import com.yurupari.common_data.model.entity.BaseEntity;
import com.yurupari.device_service.model.enums.DeviceType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "devices")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Device extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private DeviceType type;

    private String location;
    private Long userId;
}
