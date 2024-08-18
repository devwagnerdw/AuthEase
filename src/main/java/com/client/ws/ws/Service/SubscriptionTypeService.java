package com.client.ws.ws.Service;

import com.client.ws.ws.dto.SubscriptionTypeDto;
import com.client.ws.ws.model.SubscriptionType;

import java.util.List;

public interface SubscriptionTypeService {
    List<SubscriptionType> findAll();

    SubscriptionType findById(Long id);

    SubscriptionType create(SubscriptionTypeDto dto);

    SubscriptionType update(Long id, SubscriptionTypeDto dto);

    void delete(Long id);
}
