package com.client.ws.ws.model;

import com.client.ws.ws.dto.SubscriptionTypeDto;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subscriptions_type")
@Entity
public class SubscriptionType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptions_type_id")
    private Long id;
    private String name;
    @Column(name = "access_months")
    private Long accessMonth;
    private BigDecimal price;
    @Column(name = "product_key")
    private String productKey;

}