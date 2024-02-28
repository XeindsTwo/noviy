package com.example.demo27.impl;

import com.example.demo27.dao.UserDao;
import com.example.demo27.model.Role;
import com.example.demo27.model.User;
import com.example.demo27.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserDao {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void register(User user) {
        User userCandidate = userRepo.findByUsername(user.getUsername());
        if (userCandidate != null) {
            throw new IllegalArgumentException("Пользователь с таким никнеймом уже существует");
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
