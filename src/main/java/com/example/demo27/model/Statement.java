package com.example.demo27.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "statements")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotEmpty(message = "Описание не может быть пустым")
    @Size(max = 1200, message = "Длина описания максимум 1200 символов")
    private String description;

    @NotEmpty(message = "Номерной знак не может быть пустым")
    @Size(max = 12, message = "Длина номерного знака максимум 12 символов")
    private String carNumber;

    @Enumerated(EnumType.STRING)
    private StatusStatement status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
