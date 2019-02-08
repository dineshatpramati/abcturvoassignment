package com.imaginea.assignment.turvoapi.business;


import com.imaginea.assignment.turvoapi.domain.BankingService;
import com.imaginea.assignment.turvoapi.domain.Counter;
import com.imaginea.assignment.turvoapi.domain.Token;

public interface CounterAssigner {

    Counter assignCounter(BankingService service, Token token );

}

