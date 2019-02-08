package com.imaginea.assignment.turvoapi.services;


import com.imaginea.assignment.turvoapi.repositories.TokenRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QueueServiceTest {

    @Mock
    private TokenRepository tokenRepository ;



    @InjectMocks
    QueueManagerService queueManagerService;



}
