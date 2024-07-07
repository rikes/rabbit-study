package com.rikes.rabbitconsumer.domain;

import java.time.LocalDate;

public record Person (Long id,
                      String name, 
                      Integer yearOld, 
                      LocalDate bornAt, 
                      Boolean active) {
    
}
