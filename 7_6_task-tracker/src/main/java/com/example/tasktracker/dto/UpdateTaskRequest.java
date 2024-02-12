package com.example.tasktracker.dto;

import com.example.tasktracker.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {
    private String name;

    private String description;

    private TaskStatus status;
}
