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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class XmlRequestServiceImpl implements XmlRequestService {
    private static final Logger logger = LoggerFactory.getLogger(JsonRequestServiceImpl.class);


    private final XmlRequestRepository xmlRequestRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    @Value("${other.internal.service.url}")
    private String otherInternalServiceUrl;

    @Async
    public CompletableFuture<Void> processXmlCommand(XmlRequestEntity xmlRequest) {
        try {
            XmlRequestEntity savedSession = checkAndCreateSession(xmlRequest);
            // Simulate asynchronous REST API call to OTHER_INTERNAL_SERVICE
            CompletableFuture.runAsync(() -> {
                restTemplate.postForObject(otherInternalServiceUrl, xmlRequest, String.class);
            });

            xmlRequestRepository.save(savedSession);
            rabbitTemplate.convertAndSend("xml-command-exchange", "xml-command-routing-key", savedSession);
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
            return CompletableFuture.failedFuture(e);
        }
    }

    private XmlRequestEntity checkAndCreateSession(XmlRequestEntity xmlRequest) {
        Long sessionId = xmlRequest.getSessionId();
        boolean sessionExists = xmlRequestRepository.existsBySessionId(sessionId);

        if (!sessionExists) {
            String requestId = generateRequestId();
            String producerId = xmlRequest.getProducerId();
            XmlRequestEntity newSession = XmlRequestEntity.builder()
                    .sessionId(sessionId)
                    .requestId(requestId)
                    .timestamp(LocalDateTime.now())
                    .producerId(producerId)
                    .build();

            xmlRequestRepository.save(newSession);
            return newSession;
        } else {
            throw new RuntimeException("Session already exists with ID: " + sessionId);
        }
    }

    private String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}