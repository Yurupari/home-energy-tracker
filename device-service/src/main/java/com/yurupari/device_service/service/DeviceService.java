package com.yurupari.device_service.service;

import com.yurupari.device_service.model.dto.DeviceDto;

import java.util.List;

public interface DeviceService {
    DeviceDto createDevice(DeviceDto deviceDto);
    DeviceDto getDeviceById(Long id);
    void updateDevice(Long id, DeviceDto deviceDto);
    void deleteDevice(Long id);
    List<DeviceDto> getAllDevicesByUserId(Long userId);
}
