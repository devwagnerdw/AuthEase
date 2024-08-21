package com.client.ws.ws.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private CreditCardDto creditCard;

    private String customerId;

    private String orderId;

}