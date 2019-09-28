package com.rbbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitMqConsumerConfig {

    @Value("${spring.rabbitmq.listener.direct.retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${spring.rabbitmq.listener.direct.retry.initial-interval:1000}")
    private long initialInterval;

    @Value("${spring.rabbitmq.listener.direct.retry.multiplier:1}")
    private double multiplier;

    @Value("${spring.rabbitmq.listener.direct.retry.max-interval:10000}")
    private long maxInterval;


    @Value("${spring.rabbitmq.listener.max-concurrency:1}")
    private int maxConcurrency;


    @Value("${spring.rabbitmq.listener.concurrency:1}")
    private int concurrency;


    @Bean
    public RetryOperationsInterceptor interceptor() {
        return RetryInterceptorBuilder.stateless().maxAttempts(maxAttempts).backOffOptions(initialInterval, multiplier, maxInterval).recoverer(new RetryMessageRecover()).build();
    }


    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(interceptor());
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setConcurrentConsumers(concurrency);
//        factory.setMaxConcurrentConsumers(maxConcurrency);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
