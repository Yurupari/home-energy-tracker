package com.yurupari.usage_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.usage_service.client.DeviceFeignClient;
import com.yurupari.usage_service.model.dto.DeviceDto;
import com.yurupari.usage_service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class DeviceServiceImpl implements DeviceService {

    private final DeviceFeignClient deviceFeignClient;

    @Override
    public DeviceDto getDeviceById(Long id) {
        return deviceFeignClient.getDeviceById(id);
    }
}
