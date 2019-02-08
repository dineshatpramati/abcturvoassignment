package com.imaginea.assignment.turvoapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tokens")
public class Token {

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

    private int number;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "token")
    private List<TokenBankingServiceMapping> tokenServices;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "counter_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Counter counter;


    @Enumerated(EnumType.STRING)
    @NotNull
    private TokenStatus statusCode = TokenStatus.CREATED;

    public CustomerPriority getPriority() {
        return priority;
    }

    public void setPriority(CustomerPriority priority) {
        this.priority = priority;
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    private CustomerPriority priority;

    @NotNull
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<TokenBankingServiceMapping> getTokenServices() {
        return tokenServices;
    }

    public void setTokenServices(List<TokenBankingServiceMapping> tokenServices) {
        this.tokenServices = tokenServices;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }


    public TokenStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(TokenStatus statusCode) {
        this.statusCode = statusCode;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


}

