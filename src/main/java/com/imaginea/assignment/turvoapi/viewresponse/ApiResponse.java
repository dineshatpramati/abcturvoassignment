package com.imaginea.assignment.turvoapi.viewresponse;

import com.imaginea.assignment.turvoapi.crosscutting.TurvoAPIException;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {



    private T responseBody;

    private List<TurvoAPIException> errors;


    public ApiResponse(T responseBody) {
        this.responseBody = responseBody;

    }

    public ApiResponse() {
        this.errors = new ArrayList<>();
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public List<TurvoAPIException> getErrors() {
        return errors;
    }

    public void setErrors(List<TurvoAPIException> errors) {
        this.errors = errors;
    }

    public void addError(TurvoAPIException error){
        this.errors.add(error);
    }




}
