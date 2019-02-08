package com.imaginea.assignment.turvoapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TokenMessage  implements Serializable {

    private Long id ;

    private int number;

    private List<TokenBankingServiceMapping> tokenServices;

    private CustomerPriority priority;

    private int currentCounterNumber;

    private long currentServiceId;

    public long getCurrentServiceId() {
        return currentServiceId;
    }

    public void setCurrentServiceId(long currentServiceId) {
        this.currentServiceId = currentServiceId;
    }

    private long nextServiceId =-1;

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerPriority getPriority() {
        return priority;
    }

    public void setPriority(CustomerPriority priority) {
        this.priority = priority;
    }




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

    public List<TokenBankingServiceMapping> getTokenServices() {
        return tokenServices;
    }

    public TokenMessage(Long id, int tokenNumber) {
        this.id = id;
        this.number = tokenNumber;
        this.tokenServices = new ArrayList<TokenBankingServiceMapping>();
    }

    public TokenMessage() {
    }

    public void setTokenServices(List<TokenBankingServiceMapping> tokenServices) {
        this.tokenServices = tokenServices;
    }


    public void removeTokenServices(){
        this.tokenServices.remove(0);
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

    public long getNextServiceId() {
        return nextServiceId;
    }

    public void setNextServiceId(long nextServiceId) {
        this.nextServiceId = nextServiceId;
    }


}
