package com.imaginea.assignment.turvoapi.viewresponse;

import com.imaginea.assignment.turvoapi.domain.CustomerPriority;

import java.util.List;

public class CounterResponse {

   private int counterNumber ;

   private List<Integer> queuedTokens ;

   private CustomerPriority counterPriority;



    public int getCounterNumber() {
        return counterNumber;
    }

    public void setCounterNumber(int counterNumber) {
        this.counterNumber = counterNumber;
    }

    public List<Integer> getQueuedTokens() {
        return queuedTokens;
    }

    public void setQueuedTokens(List<Integer> queuedTokens) {
        this.queuedTokens = queuedTokens;
    }


    public CustomerPriority getCounterPriority() {
        return counterPriority;
    }

    public void setCounterPriority(CustomerPriority counterPriority) {
        this.counterPriority = counterPriority;
    }



}
