package com.yurupari.user_service.service;

import com.yurupari.user_service.model.dto.UserDto;

public interface UserService {
    public UserDto createUser(UserDto userDto);
}
