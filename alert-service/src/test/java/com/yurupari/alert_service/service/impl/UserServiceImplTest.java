package com.yurupari.alert_service.service.impl;

import com.yurupari.alert_service.BaseUnitTest;
import com.yurupari.alert_service.client.UserFeignClientV1;
import com.yurupari.alert_service.model.dto.UserDto;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.alert_service.constants.TestConstants.USER_WITH_ALERTING_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserFeignClientV1 userFeignClientV1;

    @Test
    void getUserById_Success() throws IOException {
        var userDto = jsonTestUtils.loadObject(USER_WITH_ALERTING_JSON, UserDto.class);

        when(userFeignClientV1.getUserById(any())).thenReturn(userDto);

        var response = userService.getUserById(1L);

        assertNotNull(response);
    }

    @Test
    void getUserById_NotFound() {
        when(userFeignClientV1.getUserById(any())).thenThrow(mock(FeignException.NotFound.class));

        var response = userService.getUserById(1L);

        assertNull(response);
    }
}