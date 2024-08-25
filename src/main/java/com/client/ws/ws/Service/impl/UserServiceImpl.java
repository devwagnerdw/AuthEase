package com.client.ws.ws.Service.impl;


import com.client.ws.ws.Service.UserService;
import com.client.ws.ws.dto.UserDto;
import com.client.ws.ws.exception.BadRequestException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.mapper.UserMapper;
import com.client.ws.ws.model.User;
import com.client.ws.ws.model.UserType;
import com.client.ws.ws.repository.UserRepository;
import com.client.ws.ws.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public User create(UserDto dto) {

        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("id deve ser nulo");
        }

        var userTypeOpt = userTypeRepository.findById(dto.getUserTypeId());

        if (userTypeOpt.isEmpty()) {
            throw new NotFoudException("userTypeId não encontrado");
        }

        UserType userType = userTypeOpt.get();
        User user = UserMapper.fromDtoToEntity(dto, userType, null);
        return userRepository.save(user);
    }


}