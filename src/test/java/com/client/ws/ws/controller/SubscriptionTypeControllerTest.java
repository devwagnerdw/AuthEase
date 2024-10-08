package com.client.ws.ws.controller;


import com.client.ws.ws.Service.SubscriptionTypeService;
import com.client.ws.ws.dto.SubscriptionTypeDto;
import com.client.ws.ws.model.jpa.SubscriptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureTestDatabase
@WebMvcTest(SubscriptionTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class SubscriptionTypeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionTypeService subscriptionTypeService;

    @Test
    void given_findAll_then_returnAllSubscriptionTYpe() throws Exception {
        mockMvc.perform(get("/subscription-type"))
                .andExpect(status().isOk());
    }

    @Test
    void given_findById_when_getId2_then_returnOneSubscriptionType () throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null,
                BigDecimal.valueOf(997), "FOREVER2022");
        when(subscriptionTypeService.findById(2L)).thenReturn(subscriptionType);

        mockMvc.perform(get("/subscription-type/2"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("VITALICIO")));
    }

    @Test
    void given_delete_whenGetId2_then_noReturnAndNoContent() throws Exception {
        mockMvc.perform(delete("/subscription-type/{id}", 2))
                .andExpect(status().isNoContent());
        verify(subscriptionTypeService, times(1)).delete(2L);
    }
    @Test
    void given_create_whendDtoIsOk_then_returnSubscriptionTypeCreated() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null,
                BigDecimal.valueOf(997), "FOREVER2022");
        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "VITALICIO", null,
                BigDecimal.valueOf(997), "FOREVER2022");
        when(subscriptionTypeService.create(dto)).thenReturn(subscriptionType);
        mockMvc.perform(post("/subscription-type")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",is(2)))
        ;
    }
    @Test
    void given_create_whendDtoIsMissingValues_then_returnBadRequest() throws Exception {
        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "", 13L,
                null, "22");
        mockMvc.perform(post("/subscription-type")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",is("[price=não pode ser nulo, accessMonths=não pode ser maior que 12, name=não pode ser nulo ou vazio, productKey=deve ter tamanho entre 5 e 15]")))
                .andExpect(jsonPath("$.httpStatus",is("BAD_REQUEST")))
                .andExpect(jsonPath("$.statusCode",is(400)))
        ;
        verify(subscriptionTypeService,times(0)).create(any());
    }
}