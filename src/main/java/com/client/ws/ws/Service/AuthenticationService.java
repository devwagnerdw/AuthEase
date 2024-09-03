package com.client.ws.ws.Service;

import com.client.ws.ws.dto.LoginDto;
import com.client.ws.ws.dto.TokenDto;

public interface AuthenticationService {

    TokenDto auth(LoginDto dto);

}
