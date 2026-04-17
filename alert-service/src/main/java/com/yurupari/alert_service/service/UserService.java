package com.yurupari.alert_service.service;

import com.yurupari.alert_service.model.dto.UserDto;

public interface UserService {

    UserDto getUserById(Long id);
}
