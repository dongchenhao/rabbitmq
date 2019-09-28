package com.rbbitmq.fanout;

import com.rbbitmq.dIrect.DirectConfig;
import com.rbbitmq.send.ProducerSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FanoutController {

    @Autowired
    private ProducerSend producerSend;

    @Autowired
    private FanoutConfig fanoutConfig;



    @PostMapping("/fanout")
    public String sendMessage(@RequestBody Object object){
        producerSend.send(null,fanoutConfig.getExchanges(),object,null);
        return  "ok";
    }
}
