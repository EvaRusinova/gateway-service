package com.example.gatewayservice.repository;

import com.example.gatewayservice.entity.XmlRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XmlRequestRepository extends JpaRepository<XmlRequestEntity, Long> {
    List<String> findCommandIdsBySessionId(Long sessionId);
    List<Long> findSessionIdsByProducerId(String userId);
    boolean existsBySessionId(Long sessionId);
}
