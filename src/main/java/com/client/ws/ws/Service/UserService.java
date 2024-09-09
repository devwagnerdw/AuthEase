package com.client.ws.ws.Service;

import com.client.ws.ws.dto.UserDto;
import com.client.ws.ws.model.jpa.User;

public interface UserService {

    User create(UserDto dto);

    Object sendRecoveryCode(String email);
}