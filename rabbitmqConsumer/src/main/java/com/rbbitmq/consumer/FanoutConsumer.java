package com.rbbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@Configuration
public class FanoutConsumer {




//    @RabbitListener(queues = "${rabbitmq.fanout.queue}")
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${rabbitmq.fanout.queue}", durable = "true"),
                    exchange = @Exchange(value = "${rabbitmq.fanout.exchange}", type = "fanout")
            ),
            containerFactory = "rabbitListenerContainerFactory"
    )
    @RabbitHandler
    public void handle(@Payload Map<String, Object> message) {
        log.info("接收到发布订阅的消息{}", message.toString());
    }


}
