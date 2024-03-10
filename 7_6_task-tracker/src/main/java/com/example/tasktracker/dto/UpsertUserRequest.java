package com.example.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено")
    @Size(min = 2, max = 60, message = "Имя пользователя должно содержать от {min} до {max}")
    private String username;

    @NotBlank(message = "Почта должна быть заполнена")
    private String email;
}
