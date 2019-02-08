package com.imaginea.assignment.turvoapi.viewresponse;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiErrorResponse {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorResponse(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
