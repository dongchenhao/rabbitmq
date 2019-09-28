package com.rbbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class DirectConsumer {


//    @RabbitListener(queues = "${rabbitmq.direct.queue}")
//    @RabbitHandler
    public void handle(@Payload Map<String, Object> message) {

        log.info("接收到的消息{}", message.toString());
    }
}
