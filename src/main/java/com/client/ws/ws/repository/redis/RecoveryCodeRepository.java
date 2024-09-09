package com.client.ws.ws.repository.redis;

import com.client.ws.ws.model.redis.RecoveryCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryCodeRepository extends CrudRepository<RecoveryCode, String> {
}