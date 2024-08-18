package com.client.ws.ws.dto;


import com.client.ws.ws.model.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionTypeDto {

    private Long id;

    private String name;

    private Long accessMonth;

    private BigDecimal price;

    private String productKey;

}
