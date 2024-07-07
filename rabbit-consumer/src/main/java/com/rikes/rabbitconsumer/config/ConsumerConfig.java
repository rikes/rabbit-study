package com.rikes.rabbitconsumer.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rikes.rabbitconsumer.domain.Person;

@Component
public class ConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(ConsumerConfig.class);
    private long MAX_RETRIES_COUNT = 3;

    @Autowired
    private RabbitTemplate rabbitTemplate;


// PEGAR O QUE FOI FEITO NO 2 METODO  E IMPLEMENTAR AQUI
// O RETRY DEVERA REENVIAR PARA A FILA PRINCIPAL

    @RabbitListener(queues = {"SECOND-QUEUE-BASIC"})
    public void receiveMessageFromTopic(Message failMessage, @Header(name = "x-death", required=false) Map<String, ?> death, Person person) {
        boolean isMaxRetry = this.checkRetryCount(death);

        //long countRetries = 1L;
        log.info("receive message from {} ", person);
        log.info("DLQ Headers: {} ", failMessage.getMessageProperties().getHeaders());
        log.info("Retrying message for the {} count", isMaxRetry);
        

        if (isMaxRetry) {
            log.info("Parking message. ID={} FIM!", person.id());
            rabbitTemplate.convertAndSend("SECOND-QUEUE-BASIC.DLX", "SECOND-QUEUE-BASIC.PRK", failMessage);
            return;
        }
        
        var payloadAsString = person.toString();
        log.info("Payload {}", payloadAsString.toString());

        if (person.yearOld().intValue() > 100){
            throw new AmqpRejectAndDontRequeueException ("Muito velho, verificar idade.");
        }

    }

    private boolean checkRetryCount(Map<String, ?> xDeath) {
        if (xDeath != null && !xDeath.isEmpty()) {
            Long count = (Long) xDeath.get("count");
            return count >= MAX_RETRIES_COUNT;
        }
        return false;
    }


    //@RabbitListener(queues = {"SECOND-QUEUE-BASIC.DLQ"})
    public void receiveMessageFromDLQ(Message failMessage, @Header(name = "x-death", required=false) Map<?, ?> death, Person person) throws InterruptedException {
        log.info("DLQ Person {} ", person);
        log.info("DLQ Headers: {} ", failMessage.getMessageProperties().getHeaders());
        long countRetries = (long) death.get("count");

        if (countRetries > MAX_RETRIES_COUNT) {
            log.info("Discarding message. ID={} FIM!", person.id());
            return;
        }
        
        log.info("Retrying message for the {} time", countRetries);
        
        //var payloadAsString = person.toString(); //message.getPayload();
        //var headerAsString = message.getHeaders();
        
        //log.info("DLQ Payload {}", payloadAsString.toString());
        //log.info("Header {}", headerAsString.toString());
    }
}
