package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.business.SequenceGenerator;
import com.imaginea.assignment.turvoapi.business.CounterAssigner;
import com.imaginea.assignment.turvoapi.crosscutting.TurvoAPIException;
import com.imaginea.assignment.turvoapi.domain.*;
import com.imaginea.assignment.turvoapi.repositories.CustomerRepository;
import com.imaginea.assignment.turvoapi.repositories.BankingServiceRepository;
import com.imaginea.assignment.turvoapi.repositories.QueueRepository;
import com.imaginea.assignment.turvoapi.repositories.TokenRepository;
import com.imaginea.assignment.turvoapi.viewresponse.TokenRequest;
import com.imaginea.assignment.turvoapi.viewresponse.TokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankingServiceRepository bankingServiceRepository;

    @Autowired
    private CounterAssigner counterAssigner;

    @Autowired
    private SequenceGenerator sequenceGenerator;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QueueRepository queueRepository;


    @Override
    @Transactional
    public TokenResponse createToken(TokenRequest tokenRequest)  throws TurvoAPIException{

        TokenResponse tokenCreated = new TokenResponse();
        try{
            Token newToken = setTokenProperties(tokenRequest);
            tokenCreated = convertToDto(tokenRepository.save(newToken));
            String queueName = newToken.getCounter().getBranch().getBranch_code()+newToken.getCounter().getNumber();
            queueRepository.enqueue(queueName,convertToMessage(newToken));
        }
        catch(TurvoAPIException ex){
            throw  ex;
        }
        catch(Exception e)
        {
           throw new TurvoAPIException(TurvoAPIException.ErrorCode.APPLICATION_ERROR);
        }
        return tokenCreated;

    }

    private Token setTokenProperties(TokenRequest tokenRequest) throws TurvoAPIException{
        Customer customer = customerRepository.findById(Long.parseLong(tokenRequest.getCustomerId())).orElse(null);

        if (customer == null) {
            throw new TurvoAPIException(TurvoAPIException.ErrorCode.CUSTOMER_NOT_FOUND);
        }

        Token token = new Token();

        //int token_sequence = generateTokenSequence();

        token.setNumber(sequenceGenerator.getNext());
        List<TokenBankingServiceMapping> tokenServices = new ArrayList<TokenBankingServiceMapping>();

        for (String s : tokenRequest.getServices()) {
            BankingService service = bankingServiceRepository.findByName(s);
            if (service == null) {
                throw new TurvoAPIException(TurvoAPIException.ErrorCode.SERVICE_NOT_FOUND);
            }
            tokenServices.add(new TokenBankingServiceMapping(token, service));

        }

        token.setTokenServices(tokenServices);
        token.setCreated(new Date());
        token.setStatusCode(TokenStatus.CREATED);
        token.setCustomer(customer);
        token.setPriority(tokenRequest.getPriority());
        Counter counter = counterAssigner.assignCounter(tokenServices.get(0).getService(),token);

        token.setCounter(counter);
        return token;

    }

    @Override
    public TokenResponse findByTokenId(long tokenId)  throws TurvoAPIException{

        try{
            return convertToDto(tokenRepository.findById(tokenId).orElse(null));
        }
        catch (TurvoAPIException ex)
        {
            throw ex;
        }
        catch (Exception e)
        {
            throw new TurvoAPIException(TurvoAPIException.ErrorCode.APPLICATION_ERROR);
        }

    }

    @Override
    @Transactional
    public void addTokenComment(long tokenId,long serviceId, String comments)  throws TurvoAPIException {

        try
        {
            Token token = checkTokenValidity(tokenId);
            TokenBankingServiceMapping current =
                    token.getTokenServices()
                            .stream()
                            .filter(tsm -> tsm.getService().getId() == serviceId)
                            .findFirst().get();
            current.setComments(comments);
        }
        catch(TurvoAPIException ex)
        {
            throw  ex;
        }
        catch (Exception e)
        {
            throw new TurvoAPIException(TurvoAPIException.ErrorCode.APPLICATION_ERROR);
        }



    }


    @Override
    @Transactional
    public void cancelToken(long tokenId)  throws TurvoAPIException {

        try
        {
            Token token = checkTokenValidity(tokenId);
            token.setStatusCode(TokenStatus.CANCELLED);
        }
        catch (TurvoAPIException ex)
        {
            throw ex;
        }
        catch (Exception e)
        {
            throw new TurvoAPIException(TurvoAPIException.ErrorCode.APPLICATION_ERROR);
        }

    }

    @Override
    @Transactional
    public void completeToken(long tokenId)  throws TurvoAPIException{

        try
        {
            Token token = checkTokenValidity(tokenId);
            token.setStatusCode(TokenStatus.COMPLETED);
        }
        catch (TurvoAPIException ex)
        {
            throw ex;
        }
        catch (Exception e)
        {
            throw new TurvoAPIException(TurvoAPIException.ErrorCode.APPLICATION_ERROR);
        }

    }

    @Override
    public TokenResponse getNextQueuedTokenByCounter(String queueName) {

        return convertMessagetoDto(queueRepository.dequeue(queueName));

    }


    private Token checkTokenValidity(Long tokenId)  throws TurvoAPIException{

        try
        {
            Token token = tokenRepository.findById(tokenId).orElse(null);
            if (token == null) {
                throw new TurvoAPIException(TurvoAPIException.ErrorCode.INVALID_TOKEN);
            } else if (!TokenStatus.CREATED.equals(token.getStatusCode())) {
                throw new TurvoAPIException(TurvoAPIException.ErrorCode.INVALID_TOKEN_STATE);
            }
            return token;
        }
        catch (TurvoAPIException ex)
        {
            throw ex;
        }
        catch (Exception e)
        {
            throw new TurvoAPIException(TurvoAPIException.ErrorCode.APPLICATION_ERROR);
        }

    }


    private TokenResponse convertToDto(Token token) {

        if(token!=null)
        {
            TokenResponse tokenResponse = modelMapper.map(token,TokenResponse.class);
            tokenResponse.setCurrentCounterNumber(token.getCounter().getNumber());
            List<String> tokenServices = new ArrayList<String>();
            for(TokenBankingServiceMapping s: token.getTokenServices())
            {
                tokenServices.add(s.getService().getName());
            }
            tokenResponse.setTokenServices(tokenServices);
            return tokenResponse;
        }
        else
            return null;


    }

    private Token convertToEntity(TokenResponse tokenResponse) {

        Token token= modelMapper.map(tokenResponse, Token.class);
        return token;

    }

    private TokenMessage convertToMessage(Token token){

        TokenMessage tokenMessage = new TokenMessage(token.getId(),token.getNumber());

        tokenMessage.setTokenServices(token.getTokenServices());
        tokenMessage.setCurrentCounterNumber(token.getCounter().getNumber());
        return tokenMessage;
    }

    private TokenResponse convertMessagetoDto(TokenMessage tokenMessage){
        if(tokenMessage!=null)
        {
            TokenResponse tokenResponse = modelMapper.map(tokenMessage,TokenResponse.class);
            tokenResponse.setCurrentCounterNumber(tokenMessage.getCurrentCounterNumber());
            List<String> tokenServices = new ArrayList<String>();
            for(TokenBankingServiceMapping s: tokenMessage.getTokenServices())
            {
                tokenServices.add(s.getService().getName());
            }
            tokenResponse.setTokenServices(tokenServices);
            return tokenResponse;
        }
        else
            return null;

    }



}
