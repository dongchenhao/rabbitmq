package com.rbbitmq.fanout;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dongchen on 2019/3/6.
 */
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.fanout")
@Data
public class FanoutConfig {

    private String exchanges;


    @Bean(name = " fanoutExchange")
    public FanoutExchange exchange() {
        return new FanoutExchange(exchanges);
    }

}