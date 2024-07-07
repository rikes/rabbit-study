package com.rikes.rabbitproducer.domain;


import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_person")
public class PersonEntity{
    
    @Id
    @GeneratedValue()
    private Long id;

    private String name = "";
    private Integer yearOld = BigInteger.ZERO.intValue();
    private Boolean active = Boolean.FALSE;
    private LocalDate bornAt = LocalDate.now();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearOld() {
        return this.yearOld;
    }

    public void setYearOld(Integer yearOld) {
        this.yearOld = yearOld;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getBornAt() {
        return this.bornAt;
    }

    public void setBornAt(LocalDate bornAt) {
        this.bornAt = bornAt;
    }

    @Override
    public String toString() {
        return "PersonEntity [id=" + id + ", name=" + name + ", yearOld=" + yearOld + ", active=" + active + ", bornAt="
                + bornAt + "]";
    }
    

}
