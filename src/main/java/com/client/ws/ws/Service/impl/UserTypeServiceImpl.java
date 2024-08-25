package com.client.ws.ws.Service.impl;


import com.client.ws.ws.Service.UserTypeService;
import com.client.ws.ws.model.UserType;
import com.client.ws.ws.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }
}