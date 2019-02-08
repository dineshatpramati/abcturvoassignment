package com.imaginea.assignment.turvoapi.repositories;


import com.imaginea.assignment.turvoapi.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long> {


}
