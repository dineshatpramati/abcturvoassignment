package com.imaginea.assignment.turvoapi.crosscutting;


public class TurvoAPIException extends RuntimeException {

    public enum ErrorCode {
        CUSTOMER_NOT_FOUND(1001, "Customer does not exist"),
        DUPLICATE_CUSTOMER(1002, "Customer already exists"),
        BRANCH_NOT_FOUND(1003, "Branch doesn't exist"),
        SERVICE_NOT_FOUND(2001, "Service not supported"),
        INVALID_TOKEN(3001, "Token is invalid"),
        INVALID_TOKEN_STATE(3002, "Token not active"),
        UNAUTHORIZED_SERVICE_REQUEST(3003, "Service Request is unauthorized ,please provide valid auth token"),
        APPLICATION_ERROR(5001,"Application error , please try later "),
        INVALID_COUNTER_NUMBER(3004,"Counter number not valid,please enter valid counter number");
        private int code;
        private String message;

        ErrorCode(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    public TurvoAPIException(ErrorCode errorCode) {
        super(errorCode.message);
    }
}

