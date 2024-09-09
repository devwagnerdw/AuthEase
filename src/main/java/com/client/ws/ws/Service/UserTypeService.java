package com.client.ws.ws.Service;


import com.client.ws.ws.model.jpa.UserType;

import java.util.List;

public interface UserTypeService {

    List<UserType> findAll();

}