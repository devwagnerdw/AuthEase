package com.client.ws.ws.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String id;

    private String customerId;

    private Long discount;

    private String productAcronym;

}