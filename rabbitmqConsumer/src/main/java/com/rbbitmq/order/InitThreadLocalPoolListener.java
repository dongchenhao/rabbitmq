package com.rbbitmq.order;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


@Slf4j
public class InitThreadLocalPoolListener implements ServletContextListener {
    /**
     * 系统初始化队列
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("执行。。。。。。。。。。。。");
        RequestThreadPool.getInstance();
    }

    /**
     * 监听器销毁执行的逻辑
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
