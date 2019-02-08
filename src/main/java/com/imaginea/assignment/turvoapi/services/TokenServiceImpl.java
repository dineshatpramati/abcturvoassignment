package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.business.SequenceGenerator;
import com.imaginea.assignment.turvoapi.business.CounterAssigner;
import com.imaginea.assignment.turvoapi.crosscutting.TurvoAPIException;
import com.imaginea.assignment.turvoapi.domain.*;
import com.imaginea.assignment.turvoapi.repositories.*;
import com.imaginea.assignment.turvoapi.viewresponse.TokenRequest;
import com.imaginea.assignment.turvoapi.viewresponse.TokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankingServiceRepository bankingServiceRepository;

    @Autowired
    private BranchRepository branchRepository;

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
            if(service.isMultiCounter())
            {
                List<BankingService> childServices = bankingServiceRepository.findByParentService(service);
                childServices.sort(Comparator.comparingInt(BankingService::getCounter_order));
                for(BankingService child:childServices)
                {
                    tokenServices.add(new TokenBankingServiceMapping(token,child));
                }
            }
            else
            {
                tokenServices.add(new TokenBankingServiceMapping(token, service));
            }

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
    @Transactional
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
    @Transactional
    public TokenResponse processNextQueuedToken(String branchCode,String counterNumber) {

        String queueName = branchCode+counterNumber;

       TokenMessage tokenToBeProcessed = queueRepository.dequeue(queueName);

       if(tokenToBeProcessed!=null)
       {
           Token token = checkTokenValidity(tokenToBeProcessed.getId());

           TokenBankingServiceMapping mapping =  token.getTokenServices()
                   .stream()
                   .filter(tsm->tsm.getService().getId().equals(tokenToBeProcessed.getCurrentServiceId())).findFirst().get();
           mapping.setServiceStatus(TokenServiceStatus.COMPLETE);

           tokenToBeProcessed.removeTokenServices();

           if(tokenToBeProcessed.getNextServiceId()!=-1){

               BankingService nextService = tokenToBeProcessed.getTokenServices().get(0).getService();

               token.setStatusCode(TokenStatus.PARTIALCOMPLETE);


               token.getCustomer().setBranch(branchRepository.findByBranchCode(branchCode));

               Counter nextCounter = counterAssigner.assignCounter(nextService,token);
               token.setCounter(nextCounter);
               queueName = nextCounter.getBranch().getBranch_code()+nextCounter.getNumber();
               tokenToBeProcessed.setCurrentCounterNumber(nextCounter.getNumber());
               tokenToBeProcessed.setCurrentServiceId(nextService.getId());
               if(tokenToBeProcessed.getTokenServices().size()>1){

                   tokenToBeProcessed.setNextServiceId(token.getTokenServices().get(1).getService().getId());
               }
               else
               {
                   tokenToBeProcessed.setNextServiceId(-1);
               }

               queueRepository.enqueue(queueName,tokenToBeProcessed);
           }
           else
           {
               token.setStatusCode(TokenStatus.COMPLETED);
               tokenToBeProcessed.setCurrentCounterNumber(0);
           }


           return convertMessagetoDto(tokenToBeProcessed);
       }

       else
       {
            return new TokenResponse();
       }

    }

    private Token checkTokenValidity(Long tokenId)  throws TurvoAPIException{

        try
        {
            Token token = tokenRepository.findById(tokenId).orElse(null);
            if (token == null) {
                throw new TurvoAPIException(TurvoAPIException.ErrorCode.INVALID_TOKEN);
            } else if (!(TokenStatus.CREATED.equals(token.getStatusCode()) || TokenStatus.PARTIALCOMPLETE.equals(token.getStatusCode()))) {
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

    private Token convertMessageToEntity(TokenMessage tokenMessage){

        Token token = modelMapper.map(tokenMessage,Token.class);
        return token;
    }


    private TokenMessage convertToMessage(Token token){

        TokenMessage tokenMessage = modelMapper.map(token,TokenMessage.class);

        tokenMessage.setTokenServices(token.getTokenServices());
        tokenMessage.setCurrentCounterNumber(token.getCounter().getNumber());
        tokenMessage.setCurrentServiceId(token.getTokenServices().get(0).getService().getId());
        if(tokenMessage.getTokenServices().size()>1) {
            tokenMessage.setNextServiceId(tokenMessage.getTokenServices().get(1).getService().getId());
        }


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
