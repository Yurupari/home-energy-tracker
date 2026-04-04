package com.yurupari.user_service.service.impl;

import com.yurupari.user_service.model.dto.UserDto;
import com.yurupari.user_service.model.entity.User;
import com.yurupari.user_service.model.mapper.UserMapper;
import com.yurupari.user_service.repository.UserRepository;
import com.yurupari.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Creating user: user={}", userDto);

        final var createdUser = userMapper.toEntity(userDto);

        final var savedUser = userRepository.save(createdUser);

        return userMapper.toDto(savedUser);
    }
}
