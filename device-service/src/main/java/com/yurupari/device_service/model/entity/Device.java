package com.yurupari.device_service.model.entity;

import com.yurupari.common_data.model.entity.BaseEntity;
import jakarta.persistence.Entity;
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
    private String type;
    private String location;
    private Long userId;
}
