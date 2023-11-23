package com.example.gatewayservice.service.impl;


import com.example.gatewayservice.entity.JsonRequestEntity;
import com.example.gatewayservice.entity.StatisticsEntity;
import com.example.gatewayservice.entity.XmlRequestEntity;
import com.example.gatewayservice.repository.JsonRequestRepository;
import com.example.gatewayservice.repository.StatisticsRepository;
import com.example.gatewayservice.repository.XmlRequestRepository;
import com.example.gatewayservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final JsonRequestRepository jsonRequestRepository;
    private final XmlRequestRepository xmlRequestRepository;
    private final StatisticsRepository statisticsRepository;

    public List<Long> getSessionIdsByProducerId(String producerId) {
        List<JsonRequestEntity> jsonRequests = jsonRequestRepository.findByProducerId(producerId);
        List<XmlRequestEntity> xmlRequests = xmlRequestRepository.findByProducerId(producerId);

        List<Long> sessionIds = jsonRequests.stream()
                .map(JsonRequestEntity::getSessionId)
                .collect(Collectors.toList());

        sessionIds.addAll(xmlRequests.stream()
                .map(XmlRequestEntity::getSessionId)
                .toList());

        // Save the results to StatisticsEntity
        saveToStatisticsEntity(producerId, sessionIds);

        return sessionIds;
    }

    private void saveToStatisticsEntity(String producerId, List<Long> sessionIds) {
        // Construct a StatisticsEntity object and save it to the repository
        StatisticsEntity statisticsEntity = StatisticsEntity.builder()
                .producerId(producerId)
                .sessionIds(sessionIds)
                .timestamp(LocalDateTime.now())
                .build();

        statisticsRepository.save(statisticsEntity);
    }
}
