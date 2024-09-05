package com.client.ws.ws;

import com.client.ws.ws.integration.MailIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailIntegrationImplTest {
    @Autowired
    private MailIntegration mailintegration;

    @Test
    void createCustomerWhenDtoOK(){
        mailintegration.send("vagnerdw1@gmail.com", "OLá Gmail","Acesso liberado");
    }
}