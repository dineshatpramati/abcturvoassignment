package com.imaginea.assignment.turvoapi.viewresponse;

import com.imaginea.assignment.turvoapi.domain.CustomerPriority;

import javax.validation.constraints.NotNull;
import java.util.List;

public  class TokenRequest {


    @NotNull
    private String customerId;


    @NotNull
    private List<String> services;

    public CustomerPriority getPriority() {
        return priority;
    }

    public void setPriority(CustomerPriority priority) {
        this.priority = priority;
    }

    @NotNull

    private CustomerPriority priority;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public  List<String>  getServices() {
        return services;
    }

    public void setServices( List<String>  services) {
        this.services = services;
    }


}