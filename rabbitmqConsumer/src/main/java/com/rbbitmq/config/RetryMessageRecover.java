package com.rbbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;

import java.io.UnsupportedEncodingException;

@Slf4j
public class RetryMessageRecover<T> implements MethodInvocationRecoverer {






    @Override
    public T recover(Object[] args, Throwable cause) {
        Message arg = (Message) args[1];
        String message = null;
        try {
            message = new String(arg.getBody(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("失败的message:{}", message);
        //todo ①可以做重发②补偿自动处理③补偿人工处理④预警

        return null;
    }
}
