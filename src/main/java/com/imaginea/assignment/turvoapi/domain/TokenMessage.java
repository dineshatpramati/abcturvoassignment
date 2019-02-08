package com.imaginea.assignment.turvoapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TokenMessage  implements Serializable {

    private Long id ;

    private int tokenNumber;

    private List<TokenBankingServiceMapping> tokenServices;



    private int currentCounterNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public List<TokenBankingServiceMapping> getTokenServices() {
        return tokenServices;
    }

    public TokenMessage(Long id, int tokenNumber) {
        this.id = id;
        this.tokenNumber = tokenNumber;
        this.tokenServices = new ArrayList<TokenBankingServiceMapping>();
    }

    public TokenMessage() {
    }

    public void setTokenServices(List<TokenBankingServiceMapping> tokenServices) {
        this.tokenServices = tokenServices;
    }

    public void addTokenService(TokenBankingServiceMapping service){
        this.tokenServices.add(service);
    }
    public int getCurrentCounterNumber() {
        return currentCounterNumber;
    }

    public void setCurrentCounterNumber(int currentCounterNumber) {
        this.currentCounterNumber = currentCounterNumber;
    }


}
