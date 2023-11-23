package com.example.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Value("${spring.rabbitmq.virtual-host}")
    private String rabbitmqVirtualHost;

    @Value("${rabbitmq.queues.json-request-q}")
    private String jsonRequestQueue;

    @Value("${rabbitmq.queues.xml-request-q}")
    private String xmlRequestQueue;

    @Value("${rabbitmq.exchanges.json-request-ex}")
    private String jsonRequestExchange;

    @Value("${rabbitmq.exchanges.xml-request-ex}")
    private String xmlRequestExchange;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        connectionFactory.setVirtualHost(rabbitmqVirtualHost);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue jsonRequestQueue() {
        return new Queue(jsonRequestQueue);
    }

    @Bean
    public Queue xmlRequestQueue() {
        return new Queue(xmlRequestQueue);
    }

    @Bean
    public Exchange jsonRequestExchange() {
        return new DirectExchange(jsonRequestExchange);
    }

    @Bean
    public Exchange xmlRequestExchange() {
        return new DirectExchange(xmlRequestExchange);
    }
}
