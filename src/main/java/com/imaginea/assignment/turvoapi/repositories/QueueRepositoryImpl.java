package com.imaginea.assignment.turvoapi.repositories;

import com.imaginea.assignment.turvoapi.domain.Token;
import com.imaginea.assignment.turvoapi.domain.TokenMessage;
import com.imaginea.assignment.turvoapi.viewresponse.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class QueueRepositoryImpl implements QueueRepository {

    @Autowired
    private RedisTemplate<String, TokenMessage> redisTemplate;


    @Override
    public TokenMessage dequeue(String queueName) {
        return redisTemplate.opsForList().rightPop(queueName);
    }

    @Override
    public void enqueue(String queueName, TokenMessage value) {
        redisTemplate.opsForList().leftPush(queueName,value);

    }

    @Override
    public long size(String queueName) {

        return redisTemplate.opsForList().size(queueName);
    }

    @Override
    public List<TokenMessage> getAllQueueItems(String queueName) {
        return redisTemplate.opsForList().range(queueName,0,-1);
    }
}
