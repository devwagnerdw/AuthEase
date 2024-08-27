package com.client.ws.ws.mapper.wsraspey;

import com.client.ws.ws.dto.wsraspay.CreditCardDto;
import com.client.ws.ws.dto.wsraspay.PaymentDto;

public class PaymentMapper {

    public static PaymentDto build(String customerId, String orderId, CreditCardDto dto) {

        return PaymentDto.builder()
                .customerId(customerId)
                .orderId(orderId)
                .creditCard(dto)
                .build();

    }

}