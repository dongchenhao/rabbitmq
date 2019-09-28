package com.rbbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ConSumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConSumerApplication.class, args);
	}

}
