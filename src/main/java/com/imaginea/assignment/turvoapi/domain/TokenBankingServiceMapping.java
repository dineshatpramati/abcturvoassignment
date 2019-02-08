package com.imaginea.assignment.turvoapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "token_service_mapping")
public class TokenBankingServiceMapping {

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

    public TokenBankingServiceMapping(Token token,BankingService service) {
        this.service = service;
        this.token = token;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id")
    private BankingService service;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "token_id")
    @JsonIgnore
    private Token token;

    public TokenBankingServiceMapping() {
    }

    @JsonIgnore
    private String comments;

    public TokenServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(TokenServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private TokenServiceStatus serviceStatus = TokenServiceStatus.INCOMPLETE;

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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}

