package com.example.gatewayservice.service.impl;


import com.example.gatewayservice.entity.CommonBaseEntity;
import com.example.gatewayservice.repository.StatisticsRepository;
import com.example.gatewayservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository statisticsRepository;
    public List<Long> getSessionIdsByUserId(String userId) {
        List<CommonBaseEntity> entities = statisticsRepository.findByUserId(userId);
        return entities.stream().map(CommonBaseEntity::getSessionId).collect(Collectors.toList());
    }
}
