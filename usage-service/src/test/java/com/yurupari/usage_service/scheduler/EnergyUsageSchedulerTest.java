package com.yurupari.usage_service.scheduler;

import com.yurupari.common_data.kafka.event.AlertingEvent;
import com.yurupari.usage_service.BaseUnitTest;
import com.yurupari.usage_service.model.DeviceEnergy;
import com.yurupari.usage_service.model.dto.DeviceDto;
import com.yurupari.usage_service.model.dto.UserDto;
import com.yurupari.usage_service.service.DeviceService;
import com.yurupari.usage_service.service.UsageService;
import com.yurupari.usage_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static com.yurupari.usage_service.constants.TestConstants.DEVICE_DTO_JSON;
import static com.yurupari.usage_service.constants.TestConstants.DEVICE_ENERGY_JSON;
import static com.yurupari.usage_service.constants.TestConstants.DEVICE_ENERGY_UNDER_THRESHOLD_JSON;
import static com.yurupari.usage_service.constants.TestConstants.USER_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnergyUsageSchedulerTest extends BaseUnitTest {

    @InjectMocks
    private EnergyUsageScheduler energyUsageScheduler;

    @Mock
    private UsageService usageService;

    @Mock
    private DeviceService deviceService;

    @Mock
    private UserService userService;

    @Mock
    private KafkaTemplate<String, AlertingEvent> kafkaTemplate;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(energyUsageScheduler, "rangeTime", 3600L);
    }

    @Test
    void aggregateDeviceEnergyUsage_AlertEventSent() throws IOException {
        var deviceEnergy = jsonTestUtils.loadObject(DEVICE_ENERGY_JSON, DeviceEnergy.class);
        when(usageService.getUsageEnergy(any(), any())).thenReturn(List.of(deviceEnergy));

        var deviceDto = jsonTestUtils.loadObject(DEVICE_DTO_JSON, DeviceDto.class);
        when(deviceService.getDeviceById(any())).thenReturn(deviceDto);

        var userDto = jsonTestUtils.loadObject(USER_DTO_JSON, UserDto.class);
        when(userService.getUserById(any())).thenReturn(userDto);

        assertDoesNotThrow(() -> energyUsageScheduler.aggregateDeviceEnergyUsage());

        verify(kafkaTemplate, times(1)).send(any(), any());
    }

    @Test
    void aggregateDeviceEnergyUsage_DeviceNotFound() throws IOException {
        var deviceEnergy = jsonTestUtils.loadObject(DEVICE_ENERGY_JSON, DeviceEnergy.class);
        when(usageService.getUsageEnergy(any(), any())).thenReturn(List.of(deviceEnergy));

        when(deviceService.getDeviceById(any())).thenReturn(null);

        assertDoesNotThrow(() -> energyUsageScheduler.aggregateDeviceEnergyUsage());

        verify(userService, never()).getUserById(any());
        verify(kafkaTemplate, never()).send(any(), any());
    }

    @Test
    void aggregateDeviceEnergyUsage_AlertEventNotSent() throws IOException {
        var deviceEnergy = jsonTestUtils.loadObject(DEVICE_ENERGY_UNDER_THRESHOLD_JSON, DeviceEnergy.class);
        when(usageService.getUsageEnergy(any(), any())).thenReturn(List.of(deviceEnergy));

        var deviceDto = jsonTestUtils.loadObject(DEVICE_DTO_JSON, DeviceDto.class);
        when(deviceService.getDeviceById(any())).thenReturn(deviceDto);

        var userDto = jsonTestUtils.loadObject(USER_DTO_JSON, UserDto.class);
        when(userService.getUserById(any())).thenReturn(userDto);

        assertDoesNotThrow(() -> energyUsageScheduler.aggregateDeviceEnergyUsage());

        verify(kafkaTemplate, never()).send(any(), any());
    }
}