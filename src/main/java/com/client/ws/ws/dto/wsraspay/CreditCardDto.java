package com.client.ws.ws.dto.wsraspay;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {
    private Long cvv;

    private String documentNumber;

    private Long installments;

    private Long month;

    private String number;

    private Long year;

}