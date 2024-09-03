package com.client.ws.ws.Service;

import org.springframework.security.core.Authentication;


public interface TokenService {

    String getToken(Long userId);

    Boolean isValid(String token);

    Long getUserId(String token);

}
