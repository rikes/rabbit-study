package com.rikes.rabbitproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rikes.rabbitproducer.domain.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}