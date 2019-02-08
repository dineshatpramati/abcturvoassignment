package com.imaginea.assignment.turvoapi.controllers;

import com.imaginea.assignment.turvoapi.crosscutting.TurvoAPIException;
import com.imaginea.assignment.turvoapi.services.TokenService;
import com.imaginea.assignment.turvoapi.viewresponse.ApiResponse;
import com.imaginea.assignment.turvoapi.viewresponse.TokenRequest;
import com.imaginea.assignment.turvoapi.viewresponse.TokenResponse;
import com.imaginea.assignment.turvoapi.viewresponse.TokenUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;


    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<ApiResponse<TokenResponse>> createToken(@RequestBody @NotNull @Valid TokenRequest tokenRequest) {

        ApiResponse<TokenResponse> apiResponse = new ApiResponse<TokenResponse>();
        TokenResponse tokenCreated = tokenService.createToken(tokenRequest);
        apiResponse.setResponseBody(tokenCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    @PutMapping(value = "/{tokenId}/{serviceId}/comment")
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR','ROLE_MANAGER','ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> addTokenComment(@PathVariable("tokenId") @NotNull Long tokenId, @PathVariable("serviceId") @NotNull Long serviceId, @RequestBody TokenUpdateRequest updateRequest) {

        ApiResponse<String> apiResponse = new ApiResponse<String>();

        tokenService.addTokenComment(tokenId, serviceId, updateRequest.getComments());
        apiResponse.setResponseBody("Comment added successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


    }


    @PutMapping(value = "/{tokenId}/cancel")
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR','ROLE_MANAGER','ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> cancelToken(@PathVariable("tokenNumber") @NotNull Long tokenId) {

        ApiResponse<String> apiResponse = new ApiResponse<String>();


        tokenService.cancelToken(tokenId);
        apiResponse.setResponseBody("Token Cancelled successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


    }

    @PutMapping(value = "/{tokenId}/complete")
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR','ROLE_MANAGER','ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> completeToken(@PathVariable("tokenId") @NotNull Long tokenId) {

        ApiResponse<String> apiResponse = new ApiResponse<String>();

        tokenService.completeToken(tokenId);
        apiResponse.setResponseBody("Token Completed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


    }


    @GetMapping(value = "/{tokenId}")
    public ResponseEntity<ApiResponse<TokenResponse>> getTokenInfo(@PathVariable("tokenId") @NotNull Long tokenId) {

        ApiResponse<TokenResponse> apiResponse = new ApiResponse<TokenResponse>();
        TokenResponse tokenDetails = tokenService.findByTokenId(tokenId);
        apiResponse.setResponseBody(tokenDetails);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @GetMapping(value = "/next/{branchCode}/{counterNumber}")
    public ResponseEntity<ApiResponse<TokenResponse>> processNextQueuedToken(@PathVariable("branchCode") @NotNull String branchCode,@PathVariable("counterNumber") @NotNull String counterNumber) {

        ApiResponse<TokenResponse> apiResponse = new ApiResponse<TokenResponse>();
        TokenResponse tokenToBeProcessed = tokenService.processNextQueuedToken(branchCode,counterNumber);
        apiResponse.setResponseBody(tokenToBeProcessed);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


    }







}

