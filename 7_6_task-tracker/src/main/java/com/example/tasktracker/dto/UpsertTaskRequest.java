package com.example.tasktracker.dto;


import com.example.tasktracker.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertTaskRequest {
    private String name;

    private String description;

    private TaskStatus status;

    private String authorId;

    private String assigneeId;

}
