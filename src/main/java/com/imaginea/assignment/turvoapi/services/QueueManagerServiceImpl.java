package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.domain.BankingService;
import com.imaginea.assignment.turvoapi.domain.Counter;
import com.imaginea.assignment.turvoapi.domain.Token;
import com.imaginea.assignment.turvoapi.domain.TokenMessage;
import com.imaginea.assignment.turvoapi.repositories.CounterRepository;
import com.imaginea.assignment.turvoapi.repositories.QueueRepository;
import com.imaginea.assignment.turvoapi.viewresponse.CounterResponse;
import com.imaginea.assignment.turvoapi.viewresponse.QueueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueManagerServiceImpl implements QueueManagerService {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private CounterRepository counterRepository;


    @Override
    public TokenMessage dequeue(String queueName) {
        return queueRepository.dequeue(queueName);
    }

    @Override
    public void enqueue(String queueName, TokenMessage value) {
        queueRepository.enqueue(queueName,value);
    }

    @Override
    public long size(String queueName) {
        return queueRepository.size(queueName);
    }

    @Override
    public List<TokenMessage> getAllQueueItems(String queueName) {
        return queueRepository.getAllQueueItems(queueName);
    }

    @Override
    public QueueResponse getAllCounterQueuesByBranchCode(String branchCode) {

        List<CounterResponse> allCounters = new ArrayList<>();

        List<Counter> branchCounters = new ArrayList<>();
        counterRepository.findAll().forEach(c->{
            if(c.getBranch().getBranch_code().equalsIgnoreCase(branchCode)){
                branchCounters.add(c);
            }});

        for(Counter counter:branchCounters){

            CounterResponse ct = new CounterResponse();
            ct.setCounterNumber(counter.getNumber());
            ct.setQueuedTokens(queueRepository.getAllQueueItems(branchCode+counter.getNumber())
                    .stream()
                    .collect(Collectors.mapping(TokenMessage::getTokenNumber,Collectors.toList())));

            ct.setCounterPriority(counter.getPriority());

            allCounters.add(ct);
        }
        return new QueueResponse(allCounters);
    }


}
