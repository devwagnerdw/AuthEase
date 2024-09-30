package com.client.ws.ws.controller;


import com.client.ws.ws.Service.UserTypeService;
import com.client.ws.ws.model.jpa.UserType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTypeService userTypeService;

    @Test
    void given_findAll_then_returnAllUserType() throws Exception {
        List<UserType> userTypeList = new ArrayList<>();

        UserType userType1 = new UserType(1l, "Professor","Professor da plataforma");
        UserType userType2 = new UserType(2l, "Administrador", "Funcionario");
        userTypeList.add(userType1);
        userTypeList.add(userType2);
        System.out.println(userTypeList);
        Mockito.when(userTypeService.findAll()).thenReturn(userTypeList);

//        mockMvc.perform(MockMvcRequestBuilders.get("/user-type"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}