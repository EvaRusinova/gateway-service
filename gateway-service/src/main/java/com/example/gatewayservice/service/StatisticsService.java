package com.example.gatewayservice.service;

import java.util.List;

public interface StatisticsService {
    List<Long> getSessionIdsByUserId(String userId);
}
