package com.example.indosport.service;

import com.example.indosport.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User getLoggedUser();

    ResponseEntity<Object> findById(Long id);
}
