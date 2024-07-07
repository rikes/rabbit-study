package com.rikes.rabbitproducer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitProducerBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitProducerBaseApplication.class, args);
	}

}
