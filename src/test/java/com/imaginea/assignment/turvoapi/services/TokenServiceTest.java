package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.domain.BankingService;
import com.imaginea.assignment.turvoapi.domain.Token;
import com.imaginea.assignment.turvoapi.domain.TokenBankingServiceMapping;
import com.imaginea.assignment.turvoapi.repositories.TokenRepository;
import com.imaginea.assignment.turvoapi.viewresponse.TokenResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository ;



    @InjectMocks
    TokenService tokenService;


    @Test
    public void testNextQueuedToken(){
        Token mockToken = new Token();
        mockToken.setNumber(3);
        TokenBankingServiceMapping mockServiceTokenMapping = new TokenBankingServiceMapping();
        BankingService mockService = new BankingService();
        mockService.setName("ServiceA");

        mockServiceTokenMapping.setService(mockService);

        List<TokenBankingServiceMapping> mockTokenServices = new ArrayList<>();
        mockTokenServices.add(mockServiceTokenMapping);
        mockToken.setTokenServices(mockTokenServices);
       when(tokenRepository.findTopByCounterNumberOrderByIdDesc(2)).thenReturn(mockToken);

       TokenResponse actual = tokenService.processNextQueuedToken("ABC_HYD_002","2");

    }

    @Test
    public void testCreateNewToken(){

    }

    @Test
    public void testCreateNewTokenNonExistingCustomer(){

    }

    @Test
    public void testCreateNewTokenNonExistingServices(){

    }

    @Test
    public void testcompleteToken(){

    }


    @Test
    public void testcompleteTokenforNonExistingToken(){


    }

    @Test
    public void testcompleteTokenforMultiCounterServiceToken(){

    }

    @Test
    public void testcompleteTokenforMultipleServiceSelectedToken(){


    }


    @Test
    public void testcompleteTokenforAlreadyCompletedToken(){

    }

    @Test
    public void testcompleteTokenforCancelledToken(){


    }


    @Test
    public void testcancelToken(){

    }


    @Test
    public void testcancelTokenforNonExistingToken(){


    }

    @Test
    public void testcancelTokenforMultiCounterServiceToken(){

    }

    @Test
    public void testcancelTokenforMultipleServiceSelectedToken(){


    }


    @Test
    public void testcancelTokenforAlreadyCompletedToken(){

    }

    @Test
    public void testcancelTokenforCancelledToken(){


    }
















}
