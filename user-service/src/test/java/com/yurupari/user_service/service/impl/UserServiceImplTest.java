package com.yurupari.user_service.service.impl;

import com.yurupari.user_service.BaseUnitTest;
import com.yurupari.user_service.exception.UserNotFoundException;
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
import java.util.Optional;

import static com.yurupari.user_service.constants.TestConstants.CREATE_USER_DTO_JSON;
import static com.yurupari.user_service.constants.TestConstants.SAVED_USER_JSON;
import static com.yurupari.user_service.constants.TestConstants.UPDATE_USER_DTO_V1_JSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    void getUserById_Success() throws IOException {
        var savedUser = jsonTestUtils.loadObject(SAVED_USER_JSON, User.class);

        when(userRepository.findById(any())).thenReturn(Optional.of(savedUser));

        var response = userService.getUserById(1L);

        assertNotNull(response);
    }

    @Test
    void getUserById_UserNotFound() throws IOException {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void updateUser_Success() throws IOException {
        var updateUserDto = jsonTestUtils.loadObject(UPDATE_USER_DTO_V1_JSON, UserDto.class);
        var savedUser = jsonTestUtils.loadObject(SAVED_USER_JSON, User.class);

        when(userRepository.findById(any())).thenReturn(Optional.of(savedUser));

        assertDoesNotThrow(() -> userService.updateUser(1L, updateUserDto));

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void updateUser_UserNotFound() throws IOException {
        var updateUserDto = jsonTestUtils.loadObject(UPDATE_USER_DTO_V1_JSON, UserDto.class);

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(30L, updateUserDto));
    }

    @Test
    void deleteUser_Success() throws IOException {
        var savedUser = jsonTestUtils.loadObject(SAVED_USER_JSON, User.class);

        when(userRepository.findById(any())).thenReturn(Optional.of(savedUser));

        assertDoesNotThrow(() -> userService.deleteUser(1L));

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void deleteUser_UserNotFound() throws IOException {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(30L));
    }
}
