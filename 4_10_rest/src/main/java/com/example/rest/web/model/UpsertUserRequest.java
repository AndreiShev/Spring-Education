package com.example.rest.web.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    @Size(min = 2, max = 30, message = "Имя пользователя не может быть меньше {min} и больше {max}")
    private String firstName;

    @NotBlank(message = "Фамилия пользователя должна быть заполнено!")
    @Size(min = 2, max = 30, message = "Фамилия пользователя не может быть меньше {min} и больше {max}")
    private String lastName;

    @NotNull(message = "Возраст должен быть заполнен")
    @Positive(message = "Возраст должен быть больше 0")
    private Integer age;

    @NotBlank(message = "Почта должна быть заполнена")
    //@Pattern(regexp = "^[A-Za-z0-9@.]$", message = "Почта содержит запрещенные символы")
    private String email;

    private String password;
}
