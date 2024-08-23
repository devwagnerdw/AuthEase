package com.client.ws.ws;

import com.client.ws.ws.dto.wsraspay.CreditCardDto;
import com.client.ws.ws.dto.wsraspay.OrderDto;
import com.client.ws.ws.dto.wsraspay.PaymentDto;
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

    @Test
    void processPaymentWhenDtoOK(){
        CreditCardDto creditCardDto = new CreditCardDto(123L,"02371386030",0L,06L,"1234123412341234",2025L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto,"66c752a5bfc9827ca0e6c0a1","66c75546bfc9827ca0e6c0a2");
        wsRaspayIntegration.processPayment(paymentDto);
    }

}