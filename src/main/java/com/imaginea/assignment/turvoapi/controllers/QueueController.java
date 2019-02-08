package com.imaginea.assignment.turvoapi.controllers;


import com.imaginea.assignment.turvoapi.services.QueueManagerService;
import com.imaginea.assignment.turvoapi.viewresponse.ApiResponse;
import com.imaginea.assignment.turvoapi.viewresponse.CounterResponse;
import com.imaginea.assignment.turvoapi.viewresponse.QueueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/queues")
public class QueueController {


    @Autowired
    private QueueManagerService queueManagerService;


    @GetMapping(value = "/{branchCode}")
    public ResponseEntity<ApiResponse<QueueResponse>> getAllQueues(@PathVariable("branchCode") @NotNull String branchCode){

        ApiResponse<QueueResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResponseBody(queueManagerService.getAllCounterQueuesByBranchCode(branchCode));

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

}
