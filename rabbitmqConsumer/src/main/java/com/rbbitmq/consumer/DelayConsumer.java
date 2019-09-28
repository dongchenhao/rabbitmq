package com.rbbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class DelayConsumer {


    @RabbitListener(queues = "process_queue")
    public void handle(@Payload Map<String, Object> message) {
        log.info("延迟消息{}", message.toString());
    }
}
