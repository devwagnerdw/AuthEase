package com.client.ws.ws.service;

import com.client.ws.ws.Service.impl.UserTypeServiceImpl;
import com.client.ws.ws.model.jpa.UserType;
import com.client.ws.ws.repository.jpa.UserTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeService;

    @Test
    void findAll() {
        List<UserType> userTypeList = new ArrayList<>();
        UserType userType1 = new UserType(1l, "Professor","Professor da plataforma");
        UserType userType2 = new UserType(2l, "Administrador", "Funcionario");
        userTypeList.add(userType1);
        userTypeList.add(userType2);

        Mockito.when(userTypeRepository.findAll()).thenReturn(userTypeList);
        var result = userTypeService.findAll();
        Assertions.assertThat(result).hasSize(2).isNotEmpty();
    }
}