package com.yurupari.usage_service.service;

import com.yurupari.usage_service.model.dto.DeviceDto;

public interface DeviceService {

    DeviceDto getDeviceById(Long id);
}
