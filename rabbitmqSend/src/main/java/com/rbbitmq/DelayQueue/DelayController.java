package com.rbbitmq.DelayQueue;

import com.rbbitmq.send.ProducerSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DelayController {

    @Autowired
    private ProducerSend producerSend;


    @PostMapping("/delay")
    public String sendMessage(@RequestBody Object object) {
        producerSend.send(DelayConfig.DELAY_ROUTING_KEY,DelayConfig.DELAY_QUEUE_EXCHANGE, object, 2000, null);
        return "ok";
    }
}
