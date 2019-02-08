package com.imaginea.assignment.turvoapi.crosscutting;

public class ServiceNotFoundException  extends  RuntimeException{


    public ServiceNotFoundException(String message) {
        super(message);
    }
}
