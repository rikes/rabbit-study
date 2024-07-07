package com.rikes.rabbitproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.impl.AMQImpl.Exchange.Bind;



@Configuration
public class DirectConfig{

    public DirectConfig(QueueConfig queueConfig){
        this.firstQueue = queueConfig.firstQueue();
        this.secondQueue = queueConfig.secondQueue();
        this.deadLetterQueue = queueConfig.deadLetterQueue();
        this.parkingQueue = queueConfig.parkingQueue();
    }
    
    private Queue firstQueue;
    private Queue secondQueue;
    private Queue deadLetterQueue;
    private Queue parkingQueue;
    
    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder
            .directExchange("DIRECT-EXCHANGE-BASIC")
            .durable(true)
            .build();
    }

    @Bean
    Binding firstDirectBinding(){
        return BindingBuilder
            .bind(firstQueue)
            .to(directExchange())
            .with(QueueConfig.FIRST_QUEUE_BASIC_ROUTING_KEY)
            .noargs();
    }

    @Bean
    Binding secondDirectBinding(){
        return BindingBuilder
            .bind(secondQueue)
            .to(directExchange())
            .with(QueueConfig.SECOND_QUEUE_BASIC_ROUTING_KEY)
            .noargs();
    }


    // Config Filas DLQ - Parking
    @Bean
    public DirectExchange deadLetterExchange() {
        //return new DirectExchange("DQL-QUEUE-BASIC.DLX");
        return ExchangeBuilder
            .directExchange(QueueConfig.SECOND_QUEUE_BASIC_DLX)
            .durable(true)
            .build();
    }

    @Bean
    public Binding deadLetterBinding(){
        return BindingBuilder
            .bind(deadLetterQueue)
            .to(deadLetterExchange())
            .with(QueueConfig.SECOND_QUEUE_BASIC_DLQ);
    }

    //Binding Parking 
    @Bean
    public Binding parkingBinding(){
        return BindingBuilder
            .bind(parkingQueue)
            .to(deadLetterExchange())
            .with(QueueConfig.SECOND_QUEUE_BASIC_PARKING);
    }

}