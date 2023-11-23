package com.example.gatewayservice.service.impl;

import com.example.gatewayservice.entity.JsonRequestEntity;
import com.example.gatewayservice.repository.JsonRequestRepository;
import com.example.gatewayservice.service.JsonRequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class JsonRequestServiceImpl implements JsonRequestService {
    private static final Logger logger = LoggerFactory.getLogger(JsonRequestServiceImpl.class);

    private final JsonRequestRepository jsonRequestRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    @Value("${other.internal.service.url}")
    private String otherInternalServiceUrl;

    @Value("${rabbitmq.queues.json-request-q}")
    private String jsonRequestQueue;

    @Value("${rabbitmq.routing-keys.json-request-rk}")
    private String jsonRequestRoutingKey;


    @Async
    public CompletableFuture<Void> processAndInsertJsonRequest(JsonRequestEntity jsonRequest) {
        try {
            checkAndCreateSession(jsonRequest);
            // Simulate asynchronous REST API call to OTHER_INTERNAL_SERVICE
            CompletableFuture<Void> restCallFuture = CompletableFuture.runAsync(() -> {
                restTemplate.postForObject(otherInternalServiceUrl, jsonRequest, String.class);
            });
            restCallFuture.join(); // Wait for the asynchronous REST call to complete
            // Publish RabbitMQ message synchronously
            rabbitTemplate.convertAndSend(jsonRequestQueue, jsonRequestRoutingKey, jsonRequest);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            logger.error("An error occurred while processing and inserting JSON request", e);
            return CompletableFuture.failedFuture(e);
        }
    }


    private void checkAndCreateSession(JsonRequestEntity jsonRequest) {
        Long sessionId = jsonRequest.getSessionId();
        List<JsonRequestEntity> existingSessions = jsonRequestRepository.findBySessionId(sessionId);

        if (existingSessions.isEmpty()) {
            String requestId = jsonRequest.getRequestId();
            JsonRequestEntity newSession = JsonRequestEntity.builder()
                    .sessionId(sessionId)
                    .requestId(requestId)
                    .timestamp(LocalDateTime.now()) // Set the desired timestamp
                    .producerId(jsonRequest.getProducerId()) // Set the producerId from the incoming request
                    .build();
            jsonRequestRepository.save(newSession);
        } else {
            logger.warn("Session already exists with ID: {}", sessionId);
        }
    }


//    private String generateRequestId() {
//        return UUID.randomUUID().toString();
//    }

    @Async
    public CompletableFuture<List<String>> processAndFindJsonRequest(JsonRequestEntity jsonRequest) {
        try {
            CompletableFuture<Void> apiCallFuture = CompletableFuture.runAsync(() -> {
                restTemplate.postForObject(otherInternalServiceUrl, jsonRequest, String.class);
            });
            List<JsonRequestEntity> jsonRequests = jsonRequestRepository.findBySessionId(jsonRequest.getSessionId());

            return apiCallFuture.thenApply(ignored -> jsonRequests.stream()
                    .map(JsonRequestEntity::getRequestId)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            // Error handling with logging
            logger.error("An error occurred while processing and finding JSON request", e);
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
    }
}
