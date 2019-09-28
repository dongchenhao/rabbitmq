package com.rbbitmq.order;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class RequestThreadPool {

    public Integer corePoolSize = 10;



    private Long keepAliveTime = 60L;

    private ExecutorService threadPool = new ThreadPoolExecutor(this.corePoolSize, this.corePoolSize,
            this.keepAliveTime, TimeUnit.SECONDS,
            new ArrayBlockingQueue(this.corePoolSize));



    public RequestThreadPool() {

//         缓存队列集合来管理所有的缓存队列
        OrderQueue requestQueue = OrderQueue.getInstance();
        for (int i = 0; i < this.corePoolSize; i++) {
            ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(this.corePoolSize);
            requestQueue.add(queue);
            // 线程池和缓存队列通过线程来绑定
            // 每个线程对应一个队列
            this.threadPool.submit(new RequestThread(queue));
        }
    }


    private static class Singleton {
        private static RequestThreadPool requestThreadPool;


        static {
            requestThreadPool = new RequestThreadPool();
        }


        public static RequestThreadPool getInstance() {
            return requestThreadPool;
        }
    }


    public static RequestThreadPool getInstance() {
        return Singleton.getInstance();
    }


}
