package com.example.gatewayservice.service;

import com.example.gatewayservice.entity.XmlRequestEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface XmlRequestService {
    CompletableFuture<Void> processXmlCommand(XmlRequestEntity xmlRequest);

    CompletableFuture<List<String>> getXmlRequests(Long sessionId);
}
