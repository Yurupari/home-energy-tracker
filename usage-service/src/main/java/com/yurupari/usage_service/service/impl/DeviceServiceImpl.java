package com.yurupari.usage_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.usage_service.client.DeviceFeignClientV1;
import com.yurupari.usage_service.model.dto.DeviceDto;
import com.yurupari.usage_service.service.DeviceService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceFeignClientV1 deviceFeignClientV1;

    @Override
    public DeviceDto getDeviceById(Long id) {
        try {
            return deviceFeignClientV1.getDeviceById(id);
        } catch (FeignException.NotFound e) {
            log.warn("Device not found: id={}", id);
            return null;
        }
    }

    @Override
    public List<DeviceDto> getAllDevicesForUser(Long userId) {
        return Optional.ofNullable(deviceFeignClientV1.getAllDevicesForUser(userId))
                .orElse(List.of());
    }
}
