package com.imaginea.assignment.turvoapi.repositories;

import com.imaginea.assignment.turvoapi.domain.Branch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BranchRepository  extends CrudRepository<Branch,Long> {

    public Branch findByBranchCode(String branchCode);

}
