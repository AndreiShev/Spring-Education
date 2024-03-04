package com.example.tasktracker.handler;

import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.UpdateTaskRequest;
import com.example.tasktracker.dto.UpsertTaskRequest;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.mapper.TaskMapper;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


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
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .flatMap(task -> getFullResponse(task)), TaskResponse.class);
    }

    public Mono<ServerResponse> createTask(ServerRequest request) {
        return request.bodyToMono(UpsertTaskRequest.class)
                .flatMap(upsertTaskRequest -> Mono.just(taskMapper.requestToTask(upsertTaskRequest)))
                .flatMap(task -> {
                    task.setId(UUID.randomUUID().toString());
                    task.setCreatedAt(Instant.now());
                    task.setStatus(TaskStatus.TODO);

                    return ServerResponse.ok().body(taskRepository.save(task)
                            .flatMap(savedTask -> getFullResponse(savedTask)), TaskResponse.class);
                });

    }

    public Mono<ServerResponse> updateTask(ServerRequest request) {
        return request.bodyToMono(UpdateTaskRequest.class)
                .flatMap(update -> Mono.just(taskMapper.requestToTask(request.pathVariable("id"), update)))
                .flatMap(task -> {
                    return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                            .flatMap(existedTask -> {
                                Utils.copyNonNullValues(task, existedTask);
                                existedTask.setUpdatedAt(Instant.now());
                                return taskRepository.save(existedTask);
                            })
                            .flatMap(updatedTask -> getFullResponse(updatedTask)), TaskResponse.class);
                });
    }

    public Mono<ServerResponse> changeAuthor(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .map(task -> {
                    task.setAuthorId(request.pathVariable("authorId"));
                    return task;
                })
                .flatMap(taskRepository::save)
                .flatMap(task -> getFullResponse(task)), TaskResponse.class);
    }

    public Mono<ServerResponse> changeAssignee(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .map(task -> {
                    task.setAssigneeId(request.pathVariable("assigneeId"));
                    return task;
                })
                .flatMap(taskRepository::save)
                .flatMap(task -> getFullResponse(task)), TaskResponse.class);
    }

    public Mono<ServerResponse> addObserver(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .map(task -> {
                    Set<String> observersId = task.getObserverIds();
                    observersId.add(request.pathVariable("observerId"));
                    task.setObserverIds(observersId);
                    return task;
                })
                .flatMap(taskRepository::save)
                .flatMap(task -> getFullResponse(task)), TaskResponse.class);
    }

    public Mono<ServerResponse> deleteTask(ServerRequest request) {
        taskRepository.deleteById(request.pathVariable("id")).subscribe();
        return ServerResponse.noContent().build();
    }


    private Mono<TaskResponse> getFullResponse(Task task) {
            Mono<User> author = userRepository.findById(task.getAuthorId());
            Mono<User> assignee = userRepository.findById(task.getAssigneeId());
            Mono<Set<User>> observers = userRepository.findAllByIdIn(task.getObserverIds()).collect(Collectors.toSet());

            return Mono.zip(author, assignee, observers)
                    .map(tuple -> {
                        User athr = tuple.getT1();
                        task.setAuthor(athr);
                        User asgn = tuple.getT2();
                        task.setAssignee(asgn);
                        Set<User> susr = tuple.getT3();
                        task.setObservers(susr);
                        return taskMapper.taskToResponse(task);
                    }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")));
    }

}
