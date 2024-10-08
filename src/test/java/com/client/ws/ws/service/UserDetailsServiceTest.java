package com.client.ws.ws.service;

import com.client.ws.ws.Service.impl.UserDetailsServiceImpl;
import com.client.ws.ws.exception.BadRequestException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.integration.MailIntegration;
import com.client.ws.ws.model.jpa.UserCredentials;
import com.client.ws.ws.model.jpa.UserType;
import com.client.ws.ws.model.redis.UserRecoveryCode;
import com.client.ws.ws.repository.jpa.UserDetailsRepository;
import com.client.ws.ws.repository.redis.UserRecoveryCodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    private static final String USERNAME_ALUNO = "vagne@email.com";
    private static final String PASSWORD_ALUNO = "senha123";
    private static final String RECOVERY_CODE_ALUNO = "4065";

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Mock
    private MailIntegration mailIntegration;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void given_loadUserByUsernameAndPass_when_thereIsUsername_then_returnUserCredentials() {
        UserCredentials userCredentials = getUserCredentials();
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));
        assertEquals(userCredentials, userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO));
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }


    @Test
    void given_loadUserByUsernameAndPass_when_thereIsNoUsername_then_throwNotFoundException() {
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.empty());
        assertEquals("Usuário não encontrado",
                assertThrows(NotFoudException.class,
                        () -> userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO)
                ).getLocalizedMessage());
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_loadUserByUsernameAndPass_when_passIsNotCorrect_then_throwBadRequestException() {
        UserCredentials userCredentials = getUserCredentials();
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));
        assertEquals("Usuário ou senha inválido",
                assertThrows(BadRequestException.class,
                        () -> userDetailsService.loadUserByUsernameAndPass(USERNAME_ALUNO, "pass")
                ).getLocalizedMessage());
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsFound_then_updateUserAndSendEmail() {
        UserRecoveryCode userRecoveryCode = getUserRecoveryCode();
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.of(userRecoveryCode));
        userDetailsService.sendRecoveryCode(USERNAME_ALUNO);
        verify(userRecoveryCodeRepository, times(1)).save(any());
        verify(mailIntegration, times(1)).send(any(), any(), any());
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsNotFound_then_SaveUserAndSendEmail() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(getUserCredentials()));
        userDetailsService.sendRecoveryCode(USERNAME_ALUNO);
        verify(userRecoveryCodeRepository, times(1)).save(any());
        verify(mailIntegration, times(1)).send(any(), any(), any());
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsNotFoundAndUserDetailsIsNotFound_then_throwNotFoundException() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.empty());
        try {
            userDetailsService.sendRecoveryCode(USERNAME_ALUNO);
        } catch (Exception e) {
            assertEquals(NotFoudException.class, e.getClass());
            assertEquals("Usuário não encontrado", e.getMessage());
        }
        verify(userRecoveryCodeRepository, times(0)).save(any());
        verify(mailIntegration, times(0)).send(any(), any(), any());
    }

    @Test
    void given_recoveryCodeIsValid_when_userIsFound_then_returnTrue() {
        ReflectionTestUtils.setField(userDetailsService,"recoveryCodeTimeout","5");
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.of(getUserRecoveryCode()));
        assertTrue(userDetailsService.recoveryCodeIsValid(RECOVERY_CODE_ALUNO, USERNAME_ALUNO));
        verify(userRecoveryCodeRepository,times(1)).findByEmail(USERNAME_ALUNO);
    }


    private UserRecoveryCode getUserRecoveryCode() {
        return new UserRecoveryCode(UUID.randomUUID().toString(), USERNAME_ALUNO, RECOVERY_CODE_ALUNO, LocalDateTime.now());
    }
    private UserCredentials getUserCredentials() {
        UserType userType = new UserType(1L, "aluno", "aluno plataforma");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new UserCredentials(1L, USERNAME_ALUNO, encoder.encode(PASSWORD_ALUNO), userType);
    }
}