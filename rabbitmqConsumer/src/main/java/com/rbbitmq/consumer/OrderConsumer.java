package com.rbbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rbbitmq.order.OrderQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

@Component
@Slf4j
public class OrderConsumer {


    @RabbitListener(queues = "${rabbitmq.direct.queue}")
    public void handle(Message message, Channel channel) throws UnsupportedEncodingException {
       String  o = new String(message.getBody(),"utf-8");
        Map<String,Object> stringObjectMap= (Map<String, Object>) JSON.parse(o);
       String id= (String) stringObjectMap.get("id");
        int h;
        int hash = (id == null) ? 0 : (h = id.hashCode()) ^ (h >> 16);
        int index = OrderQueue.getInstance().size()-1 & hash;

        OrderQueue orderQueue=OrderQueue.getInstance();
        Map<String,Object> map=new HashMap<>();
        map.put("channel",channel);
        map.put("payload",stringObjectMap);
        map.put("deliveryTag",message.getMessageProperties().getDeliveryTag());
        map.put("index",index);
        ArrayBlockingQueue<Object> objects= orderQueue.getQueue(index);
        objects.add(map);
        log.info("接收到的消息{}", message.toString());
    }


}
