package com.yurupari.user_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.common_data.model.enums.Status;
import com.yurupari.user_service.exception.UserNotFoundException;
import com.yurupari.user_service.model.dto.UserDto;
import com.yurupari.user_service.model.mapper.UserMapper;
import com.yurupari.user_service.repository.UserRepository;
import com.yurupari.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        final var createdUser = userMapper.toEntity(userDto);

        final var savedUser = userRepository.save(createdUser);

        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .filter(u -> Status.ACTIVE.equals(u.getStatus()))
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        var existingUser = userRepository.findById(id)
                .filter(u -> Status.ACTIVE.equals(u.getStatus()))
                .orElseThrow(() -> new UserNotFoundException(id));

        userMapper.updateEntityFromDto(userDto, existingUser);

        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setStatus(Status.INACTIVE);

        userRepository.save(existingUser);
    }
}
