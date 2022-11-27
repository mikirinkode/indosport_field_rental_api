package com.example.indosport.service;

import com.example.indosport.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
