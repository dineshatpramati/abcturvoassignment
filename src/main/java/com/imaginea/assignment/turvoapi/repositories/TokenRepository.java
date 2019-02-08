package com.imaginea.assignment.turvoapi.repositories;

import com.imaginea.assignment.turvoapi.domain.Counter;
import com.imaginea.assignment.turvoapi.domain.CustomerPriority;
import com.imaginea.assignment.turvoapi.domain.Token;
import com.imaginea.assignment.turvoapi.domain.TokenStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {


    @Query(value = "SELECT count(*) from tokens where status_code=?1 and counter_id=?2",nativeQuery = true)
    long findTokenQueueSizeByCounterNumber(TokenStatus status_code, int counter_id);


    List<Token> findTokensByCounterNumber(int counterId);


    Token findTopByCounterNumberOrderByIdDesc(int counterId);

}

