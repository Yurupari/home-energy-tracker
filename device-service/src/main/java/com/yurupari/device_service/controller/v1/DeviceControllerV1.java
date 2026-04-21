package com.yurupari.device_service.controller.v1;

import com.yurupari.common_data.annotation.TrackTime;
import com.yurupari.device_service.model.dto.DeviceDto;
import com.yurupari.device_service.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/device")
@TrackTime
public class DeviceControllerV1 {

    @Autowired
    private DeviceService deviceService;

    @Operation(summary = "Create a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully device created")
    })
    @PostMapping("/create")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto deviceDto) {
        var createdDevice = deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully device retrieved"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id) {
        var deviceDto = deviceService.getDeviceById(id);
        return ResponseEntity.ok(deviceDto);
    }

    @Operation(summary = "Update a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully device updated"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(
            @PathVariable Long id,
            @RequestBody DeviceDto deviceDto
    ) {
        deviceService.updateDevice(id, deviceDto);
        return ResponseEntity.ok("Device updated successfully");
    }

    @Operation(summary = "Delete a device (change status to INACTIVE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully device deleted"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all devices for an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully devices retrieved")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceDto>> getAllDevicesByUserId(@PathVariable Long userId) {
        var listDeviceDto = deviceService.getAllDevicesByUserId(userId);
        return ResponseEntity.ok(listDeviceDto);
    }
}
