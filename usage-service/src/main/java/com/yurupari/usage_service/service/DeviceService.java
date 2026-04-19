package com.yurupari.usage_service.service;

import com.yurupari.usage_service.model.dto.DeviceDto;

import java.util.List;

public interface DeviceService {

    DeviceDto getDeviceById(Long id);
    List<DeviceDto> getAllDevicesForUser(Long userId);
}
