package com.example.gatewayservice;

import com.example.gatewayservice.entity.JsonRequestEntity;
import com.example.gatewayservice.entity.XmlRequestEntity;
import com.example.gatewayservice.service.JsonRequestService;
import com.example.gatewayservice.service.XmlRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
@EnableAsync
public class GatewayServiceApplication implements CommandLineRunner {
	private final JsonRequestService jsonRequestService;
	private final XmlRequestService xmlRequestService;
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		JsonRequestEntity jsonRequest = JsonRequestEntity.builder()
				.requestId("1234er")
				.producerId("123456")
				.sessionId(1234L)
				.timestamp(LocalDateTime.now())
				.build();
		jsonRequestService.processAndInsertJsonRequest(jsonRequest);

		XmlRequestEntity xmlRequest = XmlRequestEntity.builder()
				.requestId("3434")
				.producerId("34345")
				.sessionId(4321L)
				.timestamp(LocalDateTime.now())
				.build();

		xmlRequestService.processXmlCommand(xmlRequest);


	}
}
