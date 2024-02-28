package com.example.demo27.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "unique_username")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Никнейм не может быть пустым")
    @Size(min = 5, max = 80, message = "Никнейм может иметь длину от 3 до 80 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Никнейм должен содержать только латиницу и цифры")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 120, message = "Пароль может иметь длину от 6 до 120 символов")
    private String password;

    @NotEmpty(message = "ФИО не может быть пустым")
    @Size(max = 160, message = "Макс. длина ФИО 160")
    @Pattern(regexp = "^[а-яА-ЯЁё\\s]+$", message = "ФИО может включать в себя только кириллицу и пробелы")
    private String fio;

    @NotEmpty(message = "Почта не может быть пустой")
    @Email(message = "Адрес почты введён невалидно")
    @Size(max = 100, message = "Макс. длина почты 100 символов")
    private String email;

    @NotEmpty(message = "Номер телефона не может быть пустым")
    @Pattern(regexp = "^\\+7\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}$", message = "Номер телефона пишется в формате +7(ХХХ)-ХХХ-ХХ-ХХ")
    @Size(max = 50, message = "Макс. количество цифр в телефоне - 50")
    private String phone;

    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Statement> statements;
}
