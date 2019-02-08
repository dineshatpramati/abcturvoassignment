package com.imaginea.assignment.turvoapi.crosscutting;

import com.imaginea.assignment.turvoapi.viewresponse.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;


@RestControllerAdvice
public class GlobalAPIExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalAPIExceptionHandler() {

        super();

    }


    @ExceptionHandler(value = TurvoAPIException.class)
    public String handleNotFound(TurvoAPIException ex) {
        String bodyOfResponse = ex.getMessage();
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,bodyOfResponse,bodyOfResponse);
        return bodyOfResponse;
    }










}
