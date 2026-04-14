package com.yurupari.usage_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.usage_service.client.UserFeignClient;
import com.yurupari.usage_service.model.dto.UserDto;
import com.yurupari.usage_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class UserServiceImpl implements UserService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDto getUserById(Long id) {
        return userFeignClient.getUserById(id);
    }
}
