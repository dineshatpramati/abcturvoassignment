package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.crosscutting.TurvoAPIException;
import com.imaginea.assignment.turvoapi.domain.Branch;
import com.imaginea.assignment.turvoapi.domain.Customer;
import com.imaginea.assignment.turvoapi.domain.CustomerPriority;
import com.imaginea.assignment.turvoapi.repositories.BranchRepository;
import com.imaginea.assignment.turvoapi.repositories.CustomerRepository;
import com.imaginea.assignment.turvoapi.viewresponse.CustomerRequest;
import com.imaginea.assignment.turvoapi.viewresponse.CustomerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private BranchRepository branchRepository;



    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerRequest,String branchCode) {

        Branch branch = branchRepository.findByBranchCode(branchCode);
        if(branch==null)
        {
            throw new TurvoAPIException(TurvoAPIException.ErrorCode.BRANCH_NOT_FOUND);
        }
        Customer newCustomer = convertToEntity(customerRequest);
        if(newCustomer.getId()!=null)
        {
            Customer existingCustomer = customerRepository.findById(newCustomer.getId()).orElse(null);
            if (existingCustomer != null) {
                throw new TurvoAPIException(TurvoAPIException.ErrorCode.DUPLICATE_CUSTOMER);
            }
        }
        else
        {

            newCustomer.setBranch(branch);
            newCustomer.setCreated(new Date());
            if(newCustomer.getPriority()==null)
                newCustomer.setPriority(CustomerPriority.REGULAR);

        }

        return convertToResponse(customerRepository.save(newCustomer));
    }

    @Override
    public CustomerResponse findByCustomerId(Long id) {
        return convertToResponse(customerRepository.findById(id).orElse(null));
    }

    private CustomerResponse convertToResponse(Customer customer) {

        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        return customerResponse;

    }

    private Customer convertToEntity(CustomerRequest customerRequest) {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        return customer;

    }





}
