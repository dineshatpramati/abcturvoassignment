package com.imaginea.assignment.turvoapi.viewresponse;

import com.imaginea.assignment.turvoapi.domain.CustomerPriority;

import javax.validation.constraints.NotNull;

public class CustomerRequest {

    @NotNull
    private String name ;

    @NotNull
    private String address ;

    @NotNull
    private String mobileNumber;





    public CustomerPriority getPriority() {
        return priority;
    }

    public void setPriority(CustomerPriority priority) {
        this.priority = priority;
    }

    @NotNull
    private CustomerPriority priority;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }




}
