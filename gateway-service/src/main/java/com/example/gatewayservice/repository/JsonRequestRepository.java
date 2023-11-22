package com.example.gatewayservice.repository;

import com.example.gatewayservice.entity.JsonRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JsonRequestRepository  extends JpaRepository<JsonRequestEntity, Long> {
    boolean existsBySessionId(Long sessionId);
    List<Long> findSessionIdsByProducerId(String userId);
    List<JsonRequestEntity> findBySessionId(Long sessionId);
}
