package com.rbbitmq.dIrect;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dongchen on 2019/3/6.
 */
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.direct")
@Data
public class DirectConfig {

    private String routingkey;

    private String exchange;

    private String queue;


    @Bean(name = "directQueue")
    Queue queue() {
        return new Queue(queue);
    }


    @Bean(name = "directExchange")
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }


    @Bean
    Binding binding(@Qualifier("directExchange") DirectExchange exchange, @Qualifier("directQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}