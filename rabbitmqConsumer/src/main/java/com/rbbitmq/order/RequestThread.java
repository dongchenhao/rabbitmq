package com.rbbitmq.order;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

@Slf4j
public class RequestThread implements Callable<Boolean> {
    /**
     * 队列
     */
    private ArrayBlockingQueue<Object> queue;

    public RequestThread(ArrayBlockingQueue<Object> queue) {
        this.queue = queue;
    }

    /**
     * 方法中执行具体的业务逻辑
     *
     * @return
     * @throws Exception
     */
    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {

                //  获取队列排在首位的对象，如果队列为空或者队列满了，则会被阻塞住
                Map<String, Object> request = (Map<String, Object>) this.queue.take();

                log.info("执行的队列的线程线程名称为:{}", request.get("index"));
                Channel channel = (Channel) request.get("channel");
                long deliveryTag = (long) request.get("deliveryTag");
                channel.basicAck(deliveryTag, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }
}
