package com.client.ws.ws.Service.impl;


import com.client.ws.ws.Service.AuthenticationService;
import com.client.ws.ws.Service.TokenService;
import com.client.ws.ws.Service.UserDetailsService;
import com.client.ws.ws.dto.LoginDto;
import com.client.ws.ws.dto.TokenDto;
import com.client.ws.ws.exception.BadRequestException;
import com.client.ws.ws.model.jpa.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Override
    public TokenDto auth(LoginDto dto) {
        try {
            UserCredentials userCredentials = userDetailsService.loadUserByUsernameAndPass(dto.getUsername(), dto.getPassword());
            String token = tokenService.getToken(userCredentials.getId());
            return TokenDto.builder().token(token).type("Bearer").build();
        } catch (Exception e) {
            throw new BadRequestException("Erro ao formatar token - "+e.getMessage());
        }
    }
}
