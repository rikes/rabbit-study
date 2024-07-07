package com.rikes.rabbitproducer.config;

import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    public static final String FIRST_QUEUE_BASIC = "FIRST-QUEUE-BASIC";
    public static final String FIRST_QUEUE_BASIC_ROUTING_KEY = FIRST_QUEUE_BASIC + ".RK";

    public static final String SECOND_QUEUE_BASIC = "SECOND-QUEUE-BASIC";
    public static final String SECOND_QUEUE_BASIC_ROUTING_KEY = SECOND_QUEUE_BASIC + ".RK";
    public static final String SECOND_QUEUE_BASIC_DLX = SECOND_QUEUE_BASIC + ".DLX";
    public static final String SECOND_QUEUE_BASIC_DLQ = SECOND_QUEUE_BASIC + ".DLQ";
    public static final String SECOND_QUEUE_BASIC_PARKING = SECOND_QUEUE_BASIC + ".PRK";

    @Bean
    public Queue firstQueue() {
        return QueueBuilder
            .durable(FIRST_QUEUE_BASIC)
            .build();
    }

    @Bean
    public Queue secondQueue(){
        return QueueBuilder
            .durable(SECOND_QUEUE_BASIC)
            .withArgument("x-dead-letter-exchange", SECOND_QUEUE_BASIC_DLX)
            .withArgument("x-dead-letter-routing-key", SECOND_QUEUE_BASIC_DLQ)
            .build();

    }
    //Dead Letter Queue
    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder
            .durable(SECOND_QUEUE_BASIC_DLQ)
            .deadLetterExchange("DIRECT-EXCHANGE-BASIC")
            .deadLetterRoutingKey(SECOND_QUEUE_BASIC_ROUTING_KEY)
            .ttl(30000)
            .build();
    }

    // Parking Queue OR Undelivered Queue
    @Bean
    public Queue parkingQueue(){
        return QueueBuilder
            .durable(SECOND_QUEUE_BASIC_PARKING)
            .build();
    }

}
