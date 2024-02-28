package com.example.demo27.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusStatement {
    NEW("Новое"),
    CONFIRMED("Подтверждено"),
    REJECTED("Отклонено");

    private final String status;
}
