package com.example.gatewayservice.controller;

import com.example.gatewayservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/sessions")
    public ResponseEntity<List<Long>> getSessionIdsByProducerId(@RequestParam(name = "userId") String producerId) {
        List<Long> sessionIds = statisticsService.getSessionIdsByProducerId(producerId);
        return ResponseEntity.ok(sessionIds);
    }
}
