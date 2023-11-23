package com.example.gatewayservice.service.impl;

import com.example.gatewayservice.entity.XmlRequestEntity;
import com.example.gatewayservice.repository.XmlRequestRepository;
import com.example.gatewayservice.service.XmlRequestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class XmlRequestServiceImpl implements XmlRequestService {
    private static final Logger logger = LoggerFactory.getLogger(XmlRequestServiceImpl.class);

    private final XmlRequestRepository xmlRequestRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    @Value("${other.internal.service.url}")
    private String otherInternalServiceUrl;

    @Value("${rabbitmq.queues.xml-request-q}")
    private String xmlRequestQueue;

    @Value("${rabbitmq.routing-keys.xml-request-rk}")
    private String xmlRequestRoutingKey;

    @Async
    public CompletableFuture<Void> processXmlCommand(XmlRequestEntity xmlRequest) {
        try {
            checkAndCreateSession(xmlRequest);
            // Simulate asynchronous REST API call to OTHER_INTERNAL_SERVICE
            CompletableFuture<Void> restCallFuture = CompletableFuture.runAsync(() -> {
                restTemplate.postForObject(otherInternalServiceUrl, xmlRequest, String.class);
            });
            restCallFuture.join(); // Wait for the asynchronous REST call to complete
            // Publish RabbitMQ message synchronously
            rabbitTemplate.convertAndSend(xmlRequestQueue, xmlRequestRoutingKey, xmlRequest);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            logger.error("An error occurred while processing and saving XML request", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public CompletableFuture<List<String>> getXmlRequests(Long sessionId) {
        try {
            List<String> commandIds = xmlRequestRepository.findCommandIdsBySessionId(sessionId);
            return CompletableFuture.completedFuture(commandIds);
        } catch (Exception e) {
            logger.error("An error occurred while getting XML requests", e);
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
    }

    private void checkAndCreateSession(XmlRequestEntity xmlRequest) {
        Long sessionId = xmlRequest.getSessionId();
        List<XmlRequestEntity> existingSessions = xmlRequestRepository.findBySessionId(sessionId);

        if (existingSessions.isEmpty()) {
            String requestId = xmlRequest.getRequestId();
            XmlRequestEntity newSession = XmlRequestEntity.builder()
                    .sessionId(sessionId)
                    .requestId(requestId)
                    .timestamp(LocalDateTime.now())
                    .producerId(xmlRequest.getProducerId()) // Set the producerId from the incoming request
                    .build();
            xmlRequestRepository.save(newSession);
        } else {
            logger.warn("Session already exists with ID: {}", sessionId);
        }
    }
}