package com.yurupari.usage_service.service.impl;

import com.yurupari.usage_service.BaseUnitTest;
import com.yurupari.usage_service.client.DeviceFeignClientV1;
import com.yurupari.usage_service.model.dto.DeviceDto;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.usage_service.constants.TestConstants.DEVICE_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceFeignClientV1 deviceFeignClientV1;

    @Test
    void getDeviceId_Success() throws IOException {
        var deviceDto = jsonTestUtils.loadObject(DEVICE_DTO_JSON, DeviceDto.class);

        when(deviceFeignClientV1.getDeviceById(any())).thenReturn(deviceDto);

        var response = deviceService.getDeviceById(1L);

        assertNotNull(response);
    }

    @Test
    void getDeviceId_NotFound() {
        when(deviceFeignClientV1.getDeviceById(any())).thenThrow(mock(FeignException.NotFound.class));

        var response = deviceService.getDeviceById(1L);

        assertNull(response);
    }
}