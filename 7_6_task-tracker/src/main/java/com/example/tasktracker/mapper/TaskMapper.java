package com.example.tasktracker.mapper;

import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.UpdateTaskRequest;
import com.example.tasktracker.dto.UpsertTaskRequest;
import com.example.tasktracker.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "Spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE, uses = UserMapper.class)
public interface TaskMapper {

    Task requestToTask(UpsertTaskRequest request);

    @Mapping(source = "taskId", target = "id")
    Task requestToTask(String taskId, UpsertTaskRequest request);

    @Mapping(source = "taskId", target = "id")
    Task requestToTask(String taskId, UpdateTaskRequest request);


    TaskResponse taskToResponse(Task task);





}
