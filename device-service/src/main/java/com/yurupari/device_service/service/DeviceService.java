package com.yurupari.device_service.service;

import com.yurupari.device_service.model.dto.DeviceDto;

public interface DeviceService {
    DeviceDto getDeviceById(Long id);
}
