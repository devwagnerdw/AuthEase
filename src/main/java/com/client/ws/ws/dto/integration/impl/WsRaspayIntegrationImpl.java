package com.client.ws.ws.dto.integration.impl;

import com.client.ws.ws.dto.integration.WsRaspayIntegration;
import com.client.ws.ws.dto.wsraspay.CustomerDto;
import com.client.ws.ws.dto.wsraspay.OrderDto;
import com.client.ws.ws.dto.wsraspay.PaymentDto;

import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    private RestTemplate restTemplate;

    public WsRaspayIntegrationImpl(){
        restTemplate = new RestTemplate();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {
            HttpHeaders headers = getHttpHeaders();
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto,headers);
            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange("http://localhost:8081/ws-raspay/v1/customer", HttpMethod.POST, request, CustomerDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        return null;
    }

    @Override
    public Boolean processPayment(PaymentDto dto) {
        return null;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "rasmooplus:r@sm00";
        // Codifica em Base64 usando a API nativa do Java
        String base64 = Base64.getEncoder().encodeToString(credential.getBytes());
        headers.add("Authorization", "Basic " + base64);
        return headers;
    }
}
