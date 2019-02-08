package com.imaginea.assignment.turvoapi.viewresponse;

import com.imaginea.assignment.turvoapi.domain.TokenServiceStatus;
import com.imaginea.assignment.turvoapi.domain.TokenStatus;

public class TokenUpdateRequest {


    private String comments;

    private TokenServiceStatus serviceStatusStatus;

    private TokenStatus tokenStatus;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public TokenServiceStatus getServiceStatusStatus() {
        return serviceStatusStatus;
    }

    public void setServiceStatusStatus(TokenServiceStatus serviceStatusStatus) {
        this.serviceStatusStatus = serviceStatusStatus;
    }

    public TokenStatus getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(TokenStatus tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
