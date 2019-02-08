package com.imaginea.assignment.turvoapi.viewresponse;

import java.util.List;

public class TokenResponse  {


    private int tokenNumber;

    private List<String> tokenServices;

    private int currentCounterNumber;


    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public List<String> getTokenServices() {
        return tokenServices;
    }

    public void setTokenServices(List<String> tokenServices) {
        this.tokenServices = tokenServices;
    }

    public int getCurrentCounterNumber() {
        return currentCounterNumber;
    }

    public void setCurrentCounterNumber(int currentCounterNumber) {
        this.currentCounterNumber = currentCounterNumber;
    }
}
