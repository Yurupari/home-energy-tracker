package com.yurupari.user_service.service.impl;

import com.yurupari.user_service.BaseUnitTest;
import com.yurupari.user_service.model.dto.UserDto;
import com.yurupari.user_service.model.entity.User;
import com.yurupari.user_service.model.mapper.UserMapper;
import com.yurupari.user_service.model.mapper.UserMapperImpl;
import com.yurupari.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;

import static com.yurupari.user_service.constants.TestConstants.CREATE_USER_DTO_JSON;
import static com.yurupari.user_service.constants.TestConstants.SAVED_USER_JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    void createUser() throws IOException {
        var createUserDto = jsonTestUtils.loadObject(CREATE_USER_DTO_JSON, UserDto.class);
        var savedUser = jsonTestUtils.loadObject(SAVED_USER_JSON, User.class);

        when(userRepository.save(any())).thenReturn(savedUser);

        var response = userService.createUser(createUserDto);

        assertNotNull(response);
    }
}
