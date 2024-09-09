package com.client.ws.ws.service;

import com.client.ws.ws.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    void contextLoads() {
        userService.sendRecoveryCode("email@tested.com");
    }
}