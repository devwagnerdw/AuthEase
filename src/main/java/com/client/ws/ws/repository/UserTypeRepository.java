package com.client.ws.ws.repository;

import com.client.ws.ws.model.User;
import com.client.ws.ws.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType,Long> {
}
