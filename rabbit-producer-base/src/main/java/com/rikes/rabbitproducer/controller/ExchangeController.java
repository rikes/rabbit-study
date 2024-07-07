package com.rikes.rabbitproducer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rikes.rabbitproducer.domain.Person;
import com.rikes.rabbitproducer.domain.PersonEntity;
import com.rikes.rabbitproducer.repository.PersonRepository;

@Controller
@RequestMapping("/exchanges")
public class ExchangeController {

    private static final Logger log = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PersonRepository personRepository;
    
    @PostMapping("/{exchange}/{routingKey}")
    public HttpEntity<PersonEntity> postOnExchange(
        @PathVariable String exchange,
        @PathVariable String routingKey,
        @RequestBody PersonEntity person 
    ){
        log.info("sending message {}", person.toString());
        personRepository.save(person);
        //Person personEvent = new Person(person.getId(), person.getName(), person.getYearOld(), person.getBornAt(), person.getActive());

        rabbitTemplate.convertAndSend(exchange, routingKey, person);
        return ResponseEntity.ok().body(person);
    }
}
