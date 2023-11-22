package com.example.gatewayservice.controller;

import com.example.gatewayservice.entity.JsonRequestEntity;
import com.example.gatewayservice.service.JsonRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/json_api")
public class JsonRequestController {
    private final JsonRequestService jsonRequestService;

    @PostMapping("/insert")
    public ResponseEntity<String> insertJsonRequest(@Valid @RequestBody JsonRequestEntity jsonRequest) {
        jsonRequestService.processAndInsertJsonRequest(jsonRequest);
        return ResponseEntity.ok("Request processed successfully");    }

    @PostMapping("/find")
    public ResponseEntity<List<String>> findJsonRequest(@RequestBody JsonRequestEntity jsonRequest) {
        jsonRequestService.processAndFindJsonRequest(jsonRequest);
        return ResponseEntity.ok().build();
    }
}
