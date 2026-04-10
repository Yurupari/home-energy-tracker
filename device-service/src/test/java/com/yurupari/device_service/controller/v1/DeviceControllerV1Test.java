package com.yurupari.device_service.controller.v1;

import com.yurupari.device_service.BaseUnitTest;
import com.yurupari.device_service.model.dto.DeviceDto;
import com.yurupari.device_service.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.device_service.constants.TestConstants.CREATE_DEVICE_DTO_V1_JSON;
import static com.yurupari.device_service.constants.TestConstants.SAVED_DEVICE_DTO_V1_JSON;
import static com.yurupari.device_service.constants.TestConstants.UPDATE_DEVICE_DTO_V1_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeviceControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private DeviceControllerV1 deviceControllerV1;

    @Mock
    private DeviceService deviceService;

    private DeviceDto createDeviceDto, savedDeviceDto, updateDeviceDto;

    @BeforeEach
    void setUp() throws IOException {
        createDeviceDto = jsonTestUtils.loadObject(CREATE_DEVICE_DTO_V1_JSON, DeviceDto.class);
        savedDeviceDto = jsonTestUtils.loadObject(SAVED_DEVICE_DTO_V1_JSON, DeviceDto.class);
        updateDeviceDto = jsonTestUtils.loadObject(UPDATE_DEVICE_DTO_V1_JSON, DeviceDto.class);
    }

    @Test
    void createDevice() throws IOException {
        when(deviceService.createDevice(any())).thenReturn(savedDeviceDto);

        var response = deviceControllerV1.createDevice(createDeviceDto);

        assertNotNull(response);
    }

    @Test
    void getDeviceById() throws IOException {
        when(deviceService.getDeviceById(any())).thenReturn(savedDeviceDto);

        var response = deviceControllerV1.getDeviceById(2L);

        assertNotNull(response);
    }

    @Test
    void updateDevice() throws IOException {
        assertDoesNotThrow(() -> deviceControllerV1.updateDevice(2L, updateDeviceDto));

        verify(deviceService, times(1)).updateDevice(any(), any());
    }

    @Test
    void deleteDevice() {
        assertDoesNotThrow(() -> deviceControllerV1.deleteDevice(2L));

        verify(deviceService, times(1)).deleteDevice(any());
    }
}