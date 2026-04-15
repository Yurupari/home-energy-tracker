package com.yurupari.usage_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.usage_service.client.UserFeignClientV1;
import com.yurupari.usage_service.model.dto.UserDto;
import com.yurupari.usage_service.service.UserService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserFeignClientV1 userFeignClientV1;

    @Override
    public UserDto getUserById(Long id) {
        try {
            return userFeignClientV1.getUserById(id);
        } catch (FeignException.NotFound e) {
            log.warn("User not found: id={}", id);
            return null;
        }
    }
}
