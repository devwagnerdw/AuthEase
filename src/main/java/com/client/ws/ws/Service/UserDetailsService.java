package com.client.ws.ws.Service;

import com.client.ws.ws.model.jpa.UserCredentials;

public interface UserDetailsService {

    UserCredentials loadUserByUsernameAndPass(String username, String pass);

    Object sendRecoveryCode(String email);
}
