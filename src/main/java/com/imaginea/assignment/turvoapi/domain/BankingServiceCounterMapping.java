package com.imaginea.assignment.turvoapi.domain;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "service_counter_mapping")
public class BankingServiceCounterMapping {

    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id")
    private BankingService service;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "counter_id")
    private Counter counter;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankingService getService() {
        return service;
    }

    public void setService(BankingService service) {
        this.service = service;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }


}

