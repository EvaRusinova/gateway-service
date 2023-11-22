package com.example.gatewayservice.controller;


import com.example.gatewayservice.entity.XmlRequestEntity;
import com.example.gatewayservice.service.XmlRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/xml_api")
public class XmlRequestController {
    private final XmlRequestService xmlRequestService;

    @PostMapping("/command")
    public ResponseEntity<Void> processXmlCommand(@RequestBody XmlRequestEntity xmlRequest) {
        xmlRequestService.processXmlCommand(xmlRequest);
        return ResponseEntity.ok().build();
    }
}
