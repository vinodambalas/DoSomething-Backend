package com.example.dosomething.service;

import com.example.dosomething.model.User;


public interface UserService {
    User findByEmail(String email);
    void saveUser(User user);
}
