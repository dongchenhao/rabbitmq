package com.rbbitmq.order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;


public class OrderQueue {

    private OrderQueue() {
    }

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Object>> queues = new ArrayList<>();


    private static class Singleton {
        private static OrderQueue queue;

        static {
            queue = new OrderQueue();
        }

        private static OrderQueue getInstance() {
            return queue;
        }
    }


    public static OrderQueue getInstance() {
        return Singleton.getInstance();
    }


    public void add(ArrayBlockingQueue<Object> queue) {
        this.queues.add(queue);
    }


    public ArrayBlockingQueue<Object> getQueue(int index) {
        return this.queues.get(index);
    }


    public int size() {
        return this.queues.size();
    }


}
