package com.example.tasktracker.dto;

import com.example.tasktracker.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {
    @NotBlank(message = "Наименование задачи должно быть заполнено")
    @Size(min = 2, max = 60, message = "Наименование задачи должно содержать от {min} до {max} символов")
    private String name;

    @NotBlank(message = "Наименование задачи должно быть заполнено")
    @Size(min = 2, max = 60, message = "Описание задачи должно содержать от {min} до {max}")
    private String description;

    @NotBlank(message = "Статус задачи должен быть заполнен")
    private String status;
}
