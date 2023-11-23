package com.example.gatewayservice.service;

import java.util.List;

public interface StatisticsService {
    List<Long> getSessionIdsByProducerId(String producerId);
}
