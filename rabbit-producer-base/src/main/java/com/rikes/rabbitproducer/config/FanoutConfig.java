package com.rikes.rabbitproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig{
    
    public FanoutConfig(QueueConfig queueConfig){
        this.firstQueue = queueConfig.firstQueue();
        this.secondQueue = queueConfig.secondQueue();
    }
    
    private Queue firstQueue;
    private Queue secondQueue;
    
    @Bean
    public Exchange fanoutExchange(){
        return ExchangeBuilder
            .fanoutExchange("FANOUT-EXCHANGE-BASIC")
            .durable(true)
            .build();
    }

    @Bean
    public Binding firstFanoutBinding(){
        return BindingBuilder
            .bind(firstQueue)
            .to(fanoutExchange())
            .with("")
            .noargs();
    }

    @Bean
    public Binding secondFanoutBinding(){
        return BindingBuilder
            .bind(secondQueue)
            .to(fanoutExchange())
            .with("")
            .noargs();
    }
}