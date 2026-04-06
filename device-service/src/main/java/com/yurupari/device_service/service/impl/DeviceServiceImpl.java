package com.yurupari.device_service.service.impl;

import com.yurupari.common_data.model.enums.Status;
import com.yurupari.device_service.exception.DeviceNotFoundException;
import com.yurupari.device_service.model.dto.DeviceDto;
import com.yurupari.device_service.model.mapper.DeviceMapper;
import com.yurupari.device_service.repository.DeviceRepository;
import com.yurupari.device_service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    @Override
    public DeviceDto getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .filter(d -> Status.ACTIVE.equals(d.getStatus()))
                .map(deviceMapper::toDto)
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }
}
