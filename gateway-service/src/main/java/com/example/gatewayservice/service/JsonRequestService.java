package com.example.gatewayservice.service;

import com.example.gatewayservice.entity.JsonRequestEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface JsonRequestService {
    CompletableFuture<Void> processAndInsertJsonRequest(JsonRequestEntity jsonRequest);
    CompletableFuture<List<String>> processAndFindJsonRequest(JsonRequestEntity jsonRequest);
}
