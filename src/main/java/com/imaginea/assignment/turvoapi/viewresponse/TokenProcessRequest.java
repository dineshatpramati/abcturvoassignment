package com.imaginea.assignment.turvoapi.viewresponse;

import javax.validation.constraints.NotNull;

public class TokenProcessRequest {

    @NotNull
    private Long tokenId;

    @NotNull
    private Long serviceId ;

    @NotNull
    private Long operatorId;


    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }





}
