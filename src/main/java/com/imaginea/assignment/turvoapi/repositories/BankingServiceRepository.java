package com.imaginea.assignment.turvoapi.repositories;

import com.imaginea.assignment.turvoapi.domain.BankingService;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface BankingServiceRepository extends CrudRepository<BankingService, Long> {

    BankingService findByName(String name);
    List<BankingService> findByParentService(BankingService parentService);
}

