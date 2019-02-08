package com.imaginea.assignment.turvoapi.business;

import com.imaginea.assignment.turvoapi.domain.*;
import com.imaginea.assignment.turvoapi.repositories.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CounterAssignerImpl implements CounterAssigner {


    private Map<String, List<Counter>> premiumServiceCounters = new HashMap<>();
    private Map<String, List<Counter>> normalServiceCounters = new HashMap<>();

    @Autowired
    private BankingServiceCounterMappingRepository serviceCounterMappingRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private QueueRepository queueRepository;



    @PostConstruct
    public void init() {
        serviceCounterMappingRepository.findAll().forEach(scm -> {
            if (scm.getCounter().getPriority().equals(CustomerPriority.PREMIUM)) {
                addServiceCounterMapping(scm, premiumServiceCounters);
            } else {
                addServiceCounterMapping(scm, normalServiceCounters);
            }
        });
    }

    @Override
    public Counter assignCounter(BankingService service, Token token) {


        List<Counter> counters = getServiceCountersBasedOnCustomerPriority(service, token.getPriority())
                                                                                                        .stream()
                                                                                                        .filter(c -> c.getBranch().getId().equals(token.getCustomer().getBranch().getId()))
                                                                                                        .collect(Collectors.toList());
        if (!counters.isEmpty()) {
            // Allocate counter with minimum queue size
            int minQueueSize = Integer.MAX_VALUE;
            Counter allocatedCounter = null;
            for (Counter counter : counters) {

                int queueSize = (int) queueRepository.size(counter.getBranch().getBranch_code()+counter.getNumber());
               // int queueSize = (int)tokenRepository.findTokenQueueSizeByCounterNumber(TokenStatus.CREATED,counter.getNumber());
                if (queueSize < minQueueSize) {
                    minQueueSize = queueSize;
                    allocatedCounter = counter;
                }
            }
            return allocatedCounter;
        }
        return null;
    }



    private List<Counter> getServiceCountersBasedOnCustomerPriority(BankingService service, CustomerPriority priority) {
        List<Counter> counters = Collections.emptyList();
        switch (priority) {
            case PREMIUM:
                counters = premiumServiceCounters.get(service.getName());
                break;
            case REGULAR:
                counters = normalServiceCounters.get(service.getName());
                break;
        }
        return counters;
    }

    private void addServiceCounterMapping(BankingServiceCounterMapping scm, Map<String, List<Counter>> serviceCounters) {
        String serviceName = scm.getService().getName();
        List<Counter> counters = serviceCounters.get(serviceName);
        if (counters == null) {
            counters = new ArrayList<>();
            serviceCounters.put(serviceName, counters);
        }
        counters.add(scm.getCounter());
    }
}
