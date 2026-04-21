package com.yurupari.device_service.service.impl;

import com.yurupari.device_service.BaseUnitTest;
import com.yurupari.device_service.exception.DeviceNotFoundException;
import com.yurupari.device_service.model.dto.DeviceDto;
import com.yurupari.device_service.model.entity.Device;
import com.yurupari.device_service.model.mapper.DeviceMapperImpl;
import com.yurupari.device_service.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.yurupari.device_service.constants.TestConstants.CREATE_DEVICE_DTO_V1_JSON;
import static com.yurupari.device_service.constants.TestConstants.SAVED_DEVICE_JSON;
import static com.yurupari.device_service.constants.TestConstants.UPDATE_DEVICE_DTO_V1_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeviceServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Spy
    private DeviceMapperImpl deviceMapper = new DeviceMapperImpl();

    private DeviceDto createDeviceDto, updateDeviceDto;
    private Device savedDevice;

    @BeforeEach
    void setUp() throws IOException {
        createDeviceDto = jsonTestUtils.loadObject(CREATE_DEVICE_DTO_V1_JSON, DeviceDto.class);
        updateDeviceDto = jsonTestUtils.loadObject(UPDATE_DEVICE_DTO_V1_JSON, DeviceDto.class);
        savedDevice = jsonTestUtils.loadObject(SAVED_DEVICE_JSON, Device.class);
    }

    @Test
    void createDevice_Success() {
        when(deviceRepository.save(any())).thenReturn(savedDevice);

        var response = deviceService.createDevice(createDeviceDto);

        assertNotNull(response);
    }

    @Test
    void getDeviceById_Success() {
        when(deviceRepository.findById(any())).thenReturn(Optional.of(savedDevice));

        var response = deviceService.getDeviceById(2L);

        assertNotNull(response);
    }

    @Test
    void getDeviceById_DeviceNotFound() {
        when(deviceRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(2L));
    }

    @Test
    void updateDevice_Success() {
        when(deviceRepository.findById(any())).thenReturn(Optional.of(savedDevice));

        deviceService.updateDevice(2L, updateDeviceDto);

        verify(deviceRepository, times(1)).save(any());
    }

    @Test
    void updateDevice_DeviceNotFound() {
        when(deviceRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.updateDevice(2L, updateDeviceDto));
    }

    @Test
    void deleteDevice_Success() {
        when(deviceRepository.findById(any())).thenReturn(Optional.of(savedDevice));

        assertDoesNotThrow(() -> deviceService.deleteDevice(2L));

        verify(deviceRepository, times(1)).save(any());
    }

    @Test
    void deleteDevice_DeviceNotFound() {
        when(deviceRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.deleteDevice(2L));
    }

    @Test
    void getAllDevicesByUserId_Success() {
        when(deviceRepository.findAllByUserId(any())).thenReturn(List.of(savedDevice));

        var response = deviceService.getAllDevicesByUserId(1L);

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}