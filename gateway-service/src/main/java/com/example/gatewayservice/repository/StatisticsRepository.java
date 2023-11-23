package com.example.gatewayservice.repository;

import com.example.gatewayservice.entity.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long> {
}
