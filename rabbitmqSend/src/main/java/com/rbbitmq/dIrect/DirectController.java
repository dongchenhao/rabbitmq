package com.rbbitmq.dIrect;

import com.rbbitmq.send.ProducerSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectController {

    @Autowired
    private ProducerSend producerSend;

    @Autowired
    private DirectConfig directConfig;



    @PostMapping("/direct")
    public String sendMessage(@RequestBody Object object){
        producerSend.send(directConfig.getRoutingkey(),directConfig.getExchange(),object,null);
        return  "ok";
    }
}
