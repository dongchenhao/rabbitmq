package com.rbbitmq.DelayQueue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayConfig {



    /**
     * 定义延迟队列指定名称为delay_queue_queue
     */
    public static final String DELAY_QUEUE_QUEUE = "delay_queue_queue";


    /**
     * 该交换器为直接交换器，定义了绑定process_queue队列
     */
    public final static String PROCESS_EXCHANGE = "process_exchange";


    /**
     * 定义路由键，指定delay_queue 和 process_queue的路由规则
     */
    public final static String DELAY_ROUTING_KEY = "delay";

    /**
     * 该队列没有直接消费者：而是定义了到达该队列的消息会在一定时间后过期，并在过期后进入到process queue队列中，每个消息都可以自定义自己的过期时间
     */
    public final static String DELAY_QUEUE_MSG = "delay_queue";

    /**
     * 该交换器为直接交换器，定义了绑定delay_queue_queue队列
     *
     */
    public final static String DELAY_QUEUE_EXCHANGE = "delay_queue_exchange";


    /**
     * 正常消费过期消息的队列，只有该队列有消费者
     */
    public final static String PROCESS_QUEUE = "process_queue";


    /**
     * 过期队列，全部转入到指定的x-dead-letter-xx 参数指定的交换器和路由键的队列中。
     *
     */
    @Bean
    public Queue delayQueueQueue() {
        return QueueBuilder.durable(DELAY_QUEUE_QUEUE)
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DELAY_ROUTING_KEY)
                .build();
    }



    /**
     * 延迟队列交换器
     *
     */
    @Bean
    public Exchange delayQueueExchange() {
        return ExchangeBuilder
                .directExchange(DELAY_QUEUE_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 功能描述 将过期队列绑定到过期队列交换器上，并指定路由键
     */
    @Bean
    public Binding delayQueueBinding() {
        return BindingBuilder.bind(delayQueueQueue())
                .to(delayQueueExchange())
                .with(DELAY_ROUTING_KEY).noargs();
    }
    /**
     * 延迟队列， 每个消息过期了都会自动发送给withArgument指定的exchange和指定的routing-key
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE_MSG)
                // 延迟队列需要设置的消息过期后会发往的交换器名称
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                // 延迟队列需要设置的消息过期后会发往的路由键名称
                .withArgument("x-dead-letter-routing-key", DELAY_ROUTING_KEY)
                .build();
    }


    /**
     * 正常处理消息队列， 每个消息过期了都会自动路由到该队列绑定的交换器上
     */
    @Bean
    public Queue processQueue() {
        return QueueBuilder.durable(PROCESS_QUEUE)
                .build();
    }


    /**
     * 正常处理队列交换器
     */
    @Bean
    public Exchange processExchange() {
        return ExchangeBuilder
                .directExchange(PROCESS_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 正常处理队列绑定到正常处理交换器，并制定路由键为 DELAY_ROUTING_KEY
     * 谁绑定到谁，并指定路由键
     */
    @Bean
    public Binding processBiding() {
        return BindingBuilder.bind(processQueue())
                .to(processExchange())
                .with(DELAY_ROUTING_KEY).noargs();
    }



}