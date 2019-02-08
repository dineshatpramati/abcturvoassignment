package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.viewresponse.CustomerRequest;
import com.imaginea.assignment.turvoapi.viewresponse.CustomerResponse;

public interface CustomerService {

    public CustomerResponse createCustomer(CustomerRequest customerRequest,String branchCode);

    public CustomerResponse findByCustomerId(Long id);
}
