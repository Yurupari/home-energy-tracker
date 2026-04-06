package com.yurupari.device_service.service.impl;

import com.yurupari.device_service.model.dto.DeviceDto;
import com.yurupari.device_service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    @Override
    public DeviceDto getDeviceById(Long id) {
        return null;
    }
}
