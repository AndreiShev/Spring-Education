package com.example.tasktracker.handler;

import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.UpdateTaskRequest;
import com.example.tasktracker.dto.UpsertTaskRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exception.ObjectCannotBeUpdated;
import com.example.tasktracker.mapper.TaskMapper;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.utils.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskHandler {
    /**
     *  Для сущности Task необходимо создать API, который предоставляет возможность:
     *
     * - найти все задачи (в ответе также должны находиться вложенные сущности, которые описывают автора задачи и исполнителя, а также содержат список наблюдающих за задачей);
     * - найти конкретную задачу по ID (в ответе также должны находиться вложенные сущности, которые описывают автора задачи и исполнителя, а также содержат список наблюдающих за задачей);
     * - создать задачу;
     * - обновить задачу;
     * - добавить наблюдателя в задачу;
     * - удалить задачу по ID.
     */

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public Mono<ServerResponse> getAllTask(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findAll(), Task.class);
    }

    public Mono<ServerResponse> getTaskById(ServerRequest request) {
        return ServerResponse.ok()
                .body(taskRepository.findById(request.pathVariable("id")), TaskResponse.class);
    }

    public Mono<ServerResponse> createTask(ServerRequest request) {
        return request.bodyToMono(UpsertTaskRequest.class)
                .flatMap(upsertTaskRequest -> Mono.just(taskMapper.requestToTask(upsertTaskRequest)))
                .flatMap(task -> {
                    task.setId(UUID.randomUUID().toString());
                    task.setCreatedAt(Instant.now());
                    task.setStatus(TaskStatus.TODO);
                    Flux<Task> savedTask = Flux.just(task).zipWith( userRepository.findById(task.getAssigneeId()), (a, b) -> {
                        a.setAssignee(b);
                        return a;
                    }).zipWith(userRepository.findById(task.getAuthorId()), (c, d) -> {
                        c.setAuthor(d);
                        return c;
                    }).flatMap(result -> taskRepository.save(result));

                    return ServerResponse.ok().body(savedTask
                            .map(taskk -> taskMapper.taskToResponse(taskk)), TaskResponse.class);
                });

    }

    public Mono<ServerResponse> updateTask(ServerRequest request) {
        return request.bodyToMono(UpdateTaskRequest.class)
                .flatMap(update -> Mono.just(taskMapper.requestToTask(request.pathVariable("id"), update)))
                .flatMap(task -> {
                    Mono<Task> updatedTask = taskRepository.findById(request.pathVariable("id"))
                            .flatMap(existedTask -> {
                                Utils.copyNonNullValues(task, existedTask);
                                existedTask.setUpdatedAt(Instant.now());
                                return taskRepository.save(existedTask).zipWith( userRepository.findById(existedTask.getAssigneeId()), (a, b) -> {
                                    a.setAssignee(b);
                                    return a;
                                }).zipWith(userRepository.findById(existedTask.getAuthorId()), (c, d) -> {
                                    c.setAuthor(d);
                                    return c;
                                });
                            });

                    return ServerResponse.ok().body(updatedTask.map(task1 -> taskMapper.taskToResponse(task1)), TaskResponse.class);
                });
    }

    public Mono<ServerResponse> changeAuthor(ServerRequest request) {
        return ServerResponse.ok().body(
                Flux.zip(taskRepository.findById(request.pathVariable("id")),
                    userRepository.findById(request.pathVariable("authorId")), (c, f) -> {
                    c.setAuthor(f);
                    return c;
                })
                        .flatMap(taskRepository::save)
                        .map(taskMapper::taskToResponse), TaskResponse.class);
    }

    public Mono<ServerResponse> changeAssignee(ServerRequest request) {
        return ServerResponse.ok().body(
                Flux.zip(taskRepository.findById(request.pathVariable("id")),
                                userRepository.findById(request.pathVariable("assigneeId")), (c, f) -> {
                                    c.setAuthor(f);
                                    return c;
                                })
                        .flatMap(taskRepository::save)
                        .map(taskMapper::taskToResponse), TaskResponse.class);
    }

    public Mono<ServerResponse> addObserver(ServerRequest request) {
        return ServerResponse.ok().body(
                Flux.zip(taskRepository.findById(request.pathVariable("id")),
                                userRepository.findById(request.pathVariable("observerId")), (c, f) -> {
                                     c.getObservers().add(f);
                                    return c;
                                })
                        .flatMap(savedTask -> taskRepository.save(savedTask))
                        .map(task -> taskMapper.taskToResponse(task)), TaskResponse.class);
    }

    public Mono<ServerResponse> deleteTask(ServerRequest request) {
        taskRepository.deleteById(request.pathVariable("id")).subscribe();
        return ServerResponse.noContent().build();
    }


}
