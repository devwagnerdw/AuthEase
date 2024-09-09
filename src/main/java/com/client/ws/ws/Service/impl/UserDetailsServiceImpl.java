package com.client.ws.ws.Service.impl;

import com.client.ws.ws.Service.UserDetailsService;
import com.client.ws.ws.exception.BadRequestException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.integration.MailIntegration;
import com.client.ws.ws.model.jpa.UserCredentials;
import com.client.ws.ws.model.redis.UserRecoveryCode;
import com.client.ws.ws.repository.jpa.UserDetailsRepository;
import com.client.ws.ws.repository.redis.UserRecoveryCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Autowired
    private MailIntegration mailIntegration;


    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {

        var userCredentialsOpt = userDetailsRepository.findByUsername(username);

        if (userCredentialsOpt.isEmpty()) {
            throw new NotFoudException("Usuário não encontrado");
        }

        UserCredentials userCredentials = userCredentialsOpt.get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(pass, userCredentials.getPassword())) {
            return userCredentials;
        }

        throw new BadRequestException("Usuário ou senha inválido");

    }

    @Override
    public void sendRecoveryCode(String email) {

        UserRecoveryCode userRecoveryCode;
        String code = String.format("%04d", new Random().nextInt(10000));
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {

            var user = userDetailsRepository.findByUsername(email);
            if (user.isEmpty()) {
                throw new NotFoudException("Usuário não encontrado");
            }

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);

        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }
        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreationDate(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.send(email, "Código de recuperação de conta: "+code, "Código de recuperação de conta");
    }

}
