package com.client.ws.ws.Service.impl;

import com.client.ws.ws.Service.UserDetailsService;
import com.client.ws.ws.exception.BadRequestException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.model.UserCredentials;
import com.client.ws.ws.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

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

}
