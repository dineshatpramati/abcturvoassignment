package com.imaginea.assignment.turvoapi.controllers;

import com.imaginea.assignment.turvoapi.crosscutting.TurvoAPIException;
import com.imaginea.assignment.turvoapi.services.CustomerService;
import com.imaginea.assignment.turvoapi.viewresponse.ApiResponse;
import com.imaginea.assignment.turvoapi.viewresponse.CustomerRequest;
import com.imaginea.assignment.turvoapi.viewresponse.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping(value="/{customerid}")
    @ResponseBody
    public ResponseEntity<ApiResponse<CustomerResponse>> findById(@PathVariable("customerid") @NotNull Long id) {

        ApiResponse<CustomerResponse> apiResponse = new ApiResponse<CustomerResponse>();
        try{

            CustomerResponse customerResponse = customerService.findByCustomerId(id);
            apiResponse.setResponseBody(customerResponse);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        catch(TurvoAPIException ex)
        {

            apiResponse.addError(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);

        }


    }

    @PostMapping(value = "/{branchCode}")
    public ResponseEntity<ApiResponse<CustomerResponse>> create(@RequestBody @NotNull @Valid CustomerRequest customerRequest,@PathVariable("branchCode") @NotNull String branchCode) {

        ApiResponse<CustomerResponse> apiResponse = new ApiResponse<CustomerResponse>();

        try{

            CustomerResponse customerCreated = customerService.createCustomer(customerRequest,branchCode);


            if(customerCreated.getId()!=null)
            {
                apiResponse.setResponseBody(customerCreated);
                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
            }
            else
            {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
            }

        }
        catch (TurvoAPIException ex)
        {

            apiResponse.addError(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

    }

}