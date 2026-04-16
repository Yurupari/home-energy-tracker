package com.yurupari.usage_service.client;

import com.yurupari.usage_service.model.dto.DeviceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "device-service",
        url = "${services.device-service.url}",
        path = "/api/v1/device"
)
public interface DeviceFeignClientV1 {

    @GetMapping("/{deviceId}")
    DeviceDto getDeviceById(@PathVariable("deviceId") Long id);
}
