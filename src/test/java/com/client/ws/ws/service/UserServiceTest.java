package com.client.ws.ws.service;

import com.client.ws.ws.Service.impl.UserServiceImpl;
import com.client.ws.ws.dto.UserDto;
import com.client.ws.ws.model.jpa.User;
import com.client.ws.ws.model.jpa.UserType;
import com.client.ws.ws.repository.jpa.UserRepository;
import com.client.ws.ws.repository.jpa.UserTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void given_create_when_idIsNotNullAndUserIsFound_then_returnUserCreated(){

        UserDto dto = new UserDto();
        dto.setEmail("vagne@email.com");
        dto.setCpf("11111121311");
        dto.setUserTypeId(1L);

        UserType userType = new UserType(1L, "Aluno", "Aluno da plataforma");

        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());
        user.setDtSubscription(dto.getDtSubscription());
        user.setDtExpiration(dto.getDtExpiration());
        user.setUserType(userType);
        when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(user,userService.create(dto));

        verify(userTypeRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }


}