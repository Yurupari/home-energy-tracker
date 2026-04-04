package com.yurupari.user_service.controller.v1;

import com.yurupari.user_service.BaseUnitTest;
import com.yurupari.user_service.model.dto.UserDto;
import com.yurupari.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static com.yurupari.user_service.constants.TestConstants.CREATE_USER_DTO_JSON;
import static com.yurupari.user_service.constants.TestConstants.SAVED_USER_DTO_V1_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private UserControllerV1 userControllerV1;

    @Mock
    private UserService userService;

    @Test
    void createUser() throws IOException {
        var createUserDto = jsonTestUtils.loadObject(CREATE_USER_DTO_JSON, UserDto.class);
        var savedUserDto = jsonTestUtils.loadObject(SAVED_USER_DTO_V1_JSON, UserDto.class);

        when(userService.createUser(any())).thenReturn(savedUserDto);

        var response = userService.createUser(createUserDto);

        assertNotNull(response);
    }
}