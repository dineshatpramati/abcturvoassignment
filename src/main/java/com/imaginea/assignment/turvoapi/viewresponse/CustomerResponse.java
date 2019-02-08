package com.imaginea.assignment.turvoapi.viewresponse;

import com.imaginea.assignment.turvoapi.domain.CustomerPriority;

public class CustomerResponse {

    private Long id;

    private String mobile;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String address;

    private CustomerPriority priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomerPriority getPriority() {
        return priority;
    }

    public void setPriority(CustomerPriority priority) {
        this.priority = priority;
    }
}
