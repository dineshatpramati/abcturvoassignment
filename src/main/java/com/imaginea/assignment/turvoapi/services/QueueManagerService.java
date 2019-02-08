package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.domain.BankingService;
import com.imaginea.assignment.turvoapi.domain.Counter;
import com.imaginea.assignment.turvoapi.domain.Token;
import com.imaginea.assignment.turvoapi.domain.TokenMessage;
import com.imaginea.assignment.turvoapi.viewresponse.QueueResponse;

import java.util.List;

public interface QueueManagerService {

    TokenMessage dequeue(String queueName);

    void enqueue(String queueName, TokenMessage value);

    long size(String queueName);

    List<TokenMessage> getAllQueueItems(String queueName);

    QueueResponse getAllCounterQueuesByBranchCode(String branchCode);



}
