package com.rbbitmq.send;

import com.rbbitmq.DelayQueue.ExpirationMessagePostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@Slf4j
public class ProducerSend implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }


    public void send(String routingKey, String exchange, Object message, String uniqueKey) {
        String key = StringUtils.hasLength(uniqueKey) ? uniqueKey : UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(key);
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
        } catch (Exception e) {
            log.error("异常", e);
        }
    }


    public void send(String routingKey, String exchange, Object message,long ttl, String uniqueKey) {
        String key = StringUtils.hasLength(uniqueKey) ? uniqueKey : UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(key);
        try {
        rabbitTemplate.convertAndSend(exchange,routingKey, message, new ExpirationMessagePostProcessor(ttl), correlationData);
        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送到exchange成功,id: {}", correlationData.getId());
        } else {
            log.info("消息发送到exchange失败,原因: {}", cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", message, replyCode, replyText, exchange, routingKey);
    }


}
