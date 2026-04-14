package com.yurupari.usage_service.service.impl;

import com.yurupari.usage_service.BaseUnitTest;
import com.yurupari.usage_service.client.UserFeignClient;
import com.yurupari.usage_service.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.usage_service.constants.TestConstants.USER_DTO_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserFeignClient userFeignClient;

    @Test
    void getUserById() throws IOException {
        var userDto = jsonTestUtils.loadObject(USER_DTO_JSON, UserDto.class);

        when(userFeignClient.getUserById(any())).thenReturn(userDto);

        var response = userService.getUserById(1L);

        assertNotNull(response);
    }
}