package com.yurupari.user_service.service;

import com.yurupari.user_service.model.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    void updateUser(Long id, UserDto userDto);
}
