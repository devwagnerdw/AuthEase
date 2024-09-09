package com.client.ws.ws.service;

import com.client.ws.ws.Service.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserDetailsService userDetailsService ;


    @Test
    void contextLoads() {
        userDetailsService.sendRecoveryCode("vagnerdw2@gmail.com");
    }
}