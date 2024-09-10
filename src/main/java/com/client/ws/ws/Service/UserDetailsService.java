package com.client.ws.ws.Service;

import com.client.ws.ws.dto.UserDetailsDto;
import com.client.ws.ws.model.jpa.UserCredentials;

public interface UserDetailsService {

    UserCredentials loadUserByUsernameAndPass(String username, String pass);

    void sendRecoveryCode(String email);

    boolean recoveryCodeIsValid(String recoveryCode, String email);

    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);
}
