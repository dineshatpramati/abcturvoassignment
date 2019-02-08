package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.viewresponse.TokenRequest;
import com.imaginea.assignment.turvoapi.viewresponse.TokenResponse;

public interface TokenService {

     TokenResponse createToken(TokenRequest token);

     TokenResponse findByTokenId(long tokenId);

     void addTokenComment(long tokenId,long serviceId,String comments);

     void cancelToken(long tokenId);

     void completeToken(long tokenId);

     TokenResponse getNextQueuedTokenByCounter(String queueName);





}
