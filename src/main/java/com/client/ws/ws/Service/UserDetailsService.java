package com.client.ws.ws.Service;

import com.client.ws.ws.model.UserCredentials;

public interface UserDetailsService {

    UserCredentials loadUserByUsernameAndPass(String username, String pass);


}
