package com.imaginea.assignment.turvoapi.services;

import com.imaginea.assignment.turvoapi.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<String> signin(String username, String password);

    Optional<User> signup(String username, String password, String firstName, String lastName);

    List<User> getAll();


}
