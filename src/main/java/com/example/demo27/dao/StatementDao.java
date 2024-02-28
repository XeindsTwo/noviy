package com.example.demo27.dao;

import com.example.demo27.model.Statement;
import com.example.demo27.model.StatusStatement;
import com.example.demo27.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface StatementDao {
    void save(Statement statement, Principal principal);

    void delete(Long id);

    List<Statement> findAll();

    List<Statement> findByUser(User user);

    Optional<Statement> findById(Long id);

    void updateStatement(Long id, StatusStatement status);
}
