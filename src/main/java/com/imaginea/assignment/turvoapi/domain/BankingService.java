package com.imaginea.assignment.turvoapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "banking_services")
public class BankingService {


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
    @Column(unique = true)
    private String name;



    @NotNull
    @Column(columnDefinition="BOOLEAN DEFAULT false")
    @JsonIgnore
    private boolean isMultiCounter;


    @NotNull
    @JsonIgnore
    private int  counter_order=0;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parent_service_id")
    @JsonIgnore
    private BankingService parentService;


    public List<BankingService> getChildServices() {
        return childServices;
    }

    public void setChildServices(List<BankingService> childServices) {
        this.childServices = childServices;
    }

    @OneToMany(mappedBy="parentService")
    @JsonIgnore
    private List<BankingService> childServices = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounter_order() {
        return counter_order;
    }

    public void setCounter_order(int counter_order) {
        this.counter_order = counter_order;
    }


    public boolean isMultiCounter() {
        return isMultiCounter;
    }

    public void setMultiCounter(boolean multiCounter) {
        isMultiCounter = multiCounter;
    }

    public BankingService getParentService() {
        return parentService;
    }

    public void setParentService(BankingService parentService) {
        this.parentService = parentService;
    }


}

