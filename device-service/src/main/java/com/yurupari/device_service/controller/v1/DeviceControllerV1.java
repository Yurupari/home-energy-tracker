package com.yurupari.device_service.controller.v1;

import com.yurupari.device_service.model.dto.DeviceDto;
import com.yurupari.device_service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceControllerV1 {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/create")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto deviceDto) {
        DeviceDto createdDevice = deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id) {
        DeviceDto deviceDto = deviceService.getDeviceById(id);
        return ResponseEntity.ok(deviceDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(
            @PathVariable Long id,
            @RequestBody DeviceDto deviceDto
    ) {
        deviceService.updateDevice(id, deviceDto);
        return ResponseEntity.ok("Device updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
