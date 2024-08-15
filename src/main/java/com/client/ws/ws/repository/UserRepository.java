package com.client.ws.ws.repository;

import com.client.ws.ws.model.User;
import jdk.jfr.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
