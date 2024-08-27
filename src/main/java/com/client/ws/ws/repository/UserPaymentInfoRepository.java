package com.client.ws.ws.repository;

import com.client.ws.ws.model.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo,Long> {
}
