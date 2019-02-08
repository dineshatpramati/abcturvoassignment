package com.imaginea.assignment.turvoapi.business;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


@Component
public class AtomicSequenceGenerator implements  SequenceGenerator {

    private AtomicInteger value = new AtomicInteger(1);

    @Override
    public int getNext() {
        return value.getAndIncrement();
    }
}
