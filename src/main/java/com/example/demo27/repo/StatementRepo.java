package com.example.demo27.repo;

import com.example.demo27.model.Statement;
import com.example.demo27.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatementRepo extends JpaRepository<Statement, Long> {
    @Query("select r from Statement r where r.user = :user")
    List<Statement> findByUser(@Param("user") User user);
}
