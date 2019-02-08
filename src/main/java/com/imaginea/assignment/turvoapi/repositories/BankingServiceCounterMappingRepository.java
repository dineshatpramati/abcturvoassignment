package com.imaginea.assignment.turvoapi.repositories;

import com.imaginea.assignment.turvoapi.domain.BankingServiceCounterMapping;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;


@Transactional
public interface BankingServiceCounterMappingRepository extends CrudRepository<BankingServiceCounterMapping, Long> {

}
