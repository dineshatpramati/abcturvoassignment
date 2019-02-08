package com.imaginea.assignment.turvoapi.repositories;

import com.imaginea.assignment.turvoapi.domain.TokenMessage;


import java.util.List;

public interface QueueRepository {

    TokenMessage dequeue(String queueName);

    void enqueue(String queueName, TokenMessage value);

    long size(String queueName);

    List<TokenMessage> getAllQueueItems(String queueName);

    void remove(String queueName,long index,TokenMessage message);


}
