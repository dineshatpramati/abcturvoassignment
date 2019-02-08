package com.imaginea.assignment.turvoapi.crosscutting;

public class CounterNotFoundException extends RuntimeException {



    public CounterNotFoundException(String message) {
        super(message);
    }
}
