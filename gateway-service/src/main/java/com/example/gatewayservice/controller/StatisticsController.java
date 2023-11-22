package com.example.gatewayservice.controller;

import com.example.gatewayservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/user/sessions")
    public ResponseEntity<List<Long>> getSessionIdsByUserId(@PathVariable String userId) {
        List<Long> sessionIds = statisticsService.getSessionIdsByUserId(userId);
        return ResponseEntity.ok(sessionIds);
    }
}
