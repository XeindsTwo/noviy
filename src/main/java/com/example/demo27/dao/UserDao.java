package com.example.demo27.dao;

import com.example.demo27.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);

    User findByUsername(String username);
}
