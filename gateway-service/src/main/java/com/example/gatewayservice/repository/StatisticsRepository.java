package com.example.gatewayservice.repository;

import com.example.gatewayservice.entity.CommonBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticsRepository extends JpaRepository <CommonBaseEntity, Long>{
    List<CommonBaseEntity> findByUserId(String userId);

}
