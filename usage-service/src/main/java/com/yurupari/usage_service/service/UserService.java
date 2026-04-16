package com.yurupari.usage_service.service;

import com.yurupari.usage_service.model.dto.UserDto;

public interface UserService {

    UserDto getUserById(Long id);
}
