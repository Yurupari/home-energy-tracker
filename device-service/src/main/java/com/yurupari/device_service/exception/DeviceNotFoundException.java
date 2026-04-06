package com.yurupari.device_service.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(Long id) {
        super(String.format("Device not found: id=%s", id));
    }
}
