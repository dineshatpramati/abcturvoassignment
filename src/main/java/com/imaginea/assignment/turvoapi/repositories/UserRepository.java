package com.imaginea.assignment.turvoapi.repositories;

import com.imaginea.assignment.turvoapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String userName);
}
