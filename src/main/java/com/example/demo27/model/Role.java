package com.example.demo27.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("Администратор"),
    USER("Пользователь");
    private final String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
