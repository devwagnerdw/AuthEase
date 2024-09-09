package com.client.ws.ws.repository.jpa;

import com.client.ws.ws.model.jpa.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType,Long> {
    Optional<SubscriptionType> findByProductKey(String productKey);
}
