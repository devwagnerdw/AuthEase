package com.client.ws.ws.service;

import com.client.ws.ws.Service.impl.UserDetailsServiceImpl;
import com.client.ws.ws.exception.BadRequestException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.model.jpa.UserCredentials;
import com.client.ws.ws.model.jpa.UserType;
import com.client.ws.ws.repository.jpa.UserDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
    private static final String USERNAME_ALUNO = "vagne@email.com";
    private static final String PASSWORD_ALUNO = "123";
    @Mock
    private UserDetailsRepository userDetailsRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    private UserCredentials userCredentials;
    @BeforeEach
    public void loadUserCredentials() {
        UserType userType = new UserType(1L, "aluno", "aluno plataforma");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userCredentials = new UserCredentials(1L, USERNAME_ALUNO, encoder.encode(PASSWORD_ALUNO), userType);
    }
    @BeforeEach
    public void loadUserRecoveryCode() {
    }

    @Test
    void given_loadUserByUsernameAndPass_when_thereIsUsername_then_returnUserCredentials() {
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));
        assertEquals(userCredentials, userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO));
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_loadUserByUsernameAndPass_when_thereIsNoUsername_then_throwNotFoundException() {
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.empty());
        assertEquals(
                assertThrows(NotFoudException.class,
                        () -> userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO)
                ).getLocalizedMessage(), "Usuário não encontrado");
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }
    @Test
    void given_loadUserByUsernameAndPass_when_passIsNotCorrect_then_throwBadRequestException() {
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));
        assertEquals(
                assertThrows(BadRequestException.class,
                        () -> userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, "pass")
                ).getLocalizedMessage(), "Usuário ou senha inválido");
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }
}