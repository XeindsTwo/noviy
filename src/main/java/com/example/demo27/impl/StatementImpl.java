package com.example.demo27.impl;

import com.example.demo27.dao.StatementDao;
import com.example.demo27.dao.UserDao;
import com.example.demo27.model.Statement;
import com.example.demo27.model.StatusStatement;
import com.example.demo27.model.User;
import com.example.demo27.repo.StatementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatementImpl implements StatementDao {
    private final StatementRepo statementRepo;
    private final UserDao userDao;

    @Override
    public void save(Statement statement, Principal principal) {
        String username = principal.getName();
        User user = userDao.findByUsername(username);
        statement.setStatus(StatusStatement.NEW);
        statement.setUser(user);
        statementRepo.save(statement);
    }

    @Override
    public void delete(Long id) {
        Statement statement = statementRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявление не найдено"));
        statementRepo.delete(statement);
    }

    @Override
    public List<Statement> findAll() {
        return statementRepo.findAll();
    }

    @Override
    public List<Statement> findByUser(User user) {
        return statementRepo.findByUser(user);
    }

    @Override
    public Optional<Statement> findById(Long id) {
        return statementRepo.findById(id);
    }

    @Override
    public void updateStatement(Long id, StatusStatement status) {
        Statement statement = statementRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявление не найдено " + id));
        statement.setStatus(status);
        statementRepo.save(statement);
    }
}
