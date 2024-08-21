package com.client.ws.ws;

import com.client.ws.ws.dto.integration.WsRaspayIntegration;
import com.client.ws.ws.dto.wsraspay.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WsRaspayIntegrationImplTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOK(){
        CustomerDto dto = new CustomerDto(null,"02371386030","teste@gmail.com","vagne","Alves");
        wsRaspayIntegration.createCustomer(dto);
    }

}