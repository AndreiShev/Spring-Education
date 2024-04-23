package com.example.hotelbooking.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 10, message = "Пароль должен содержать не менее {min} символов")
    private String password;

    @NotBlank(message = "Почта не может быть пустой")
    private String email;

    @NotBlank(message = "Роль пользователя не может быть пустой")
    private String userRole;
}
