package com.client.ws.ws.repository.jpa;

import com.client.ws.ws.model.jpa.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo,Long> {
}
