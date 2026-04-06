package com.yurupari.device_service.repository;

import com.yurupari.device_service.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
