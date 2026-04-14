package com.yurupari.usage_service.service.impl;

import com.yurupari.usage_service.BaseUnitTest;
import com.yurupari.usage_service.client.DeviceFeignClient;
import com.yurupari.usage_service.model.dto.DeviceDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.usage_service.constants.TestConstants.DEVICE_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DeviceServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceFeignClient deviceFeignClient;

    @Test
    void getDeviceId() throws IOException {
        var deviceDto = jsonTestUtils.loadObject(DEVICE_DTO_JSON, DeviceDto.class);

        when(deviceFeignClient.getDeviceById(any())).thenReturn(deviceDto);

        var response = deviceService.getDeviceById(1L);

        assertNotNull(response);
    }
}