package com.client.ws.ws;

import com.client.ws.ws.dto.wsraspay.OrderDto;
import com.client.ws.ws.integration.WsRaspayIntegration;
import com.client.ws.ws.dto.wsraspay.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class WsRaspayIntegrationImplTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOK(){
        CustomerDto dto = new CustomerDto(null,"02371386030","teste@gmail.com","vagne","Alves");
        wsRaspayIntegration.createCustomer(dto);
    }

    @Test
    void createOrderWhenDtoOK(){
        OrderDto dto = new OrderDto(null,"66c752a5bfc9827ca0e6c0a1", BigDecimal.ZERO,"MONTH22");
        wsRaspayIntegration.createOrder(dto);
    }

}