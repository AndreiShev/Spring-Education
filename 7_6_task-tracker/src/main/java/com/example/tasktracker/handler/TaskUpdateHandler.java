package com.example.tasktracker.handler;

import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.UpdateTaskRequest;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exceptions.EntityNotFoundException;
import com.example.tasktracker.mapper.TaskMapper;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskUpdateHandler extends AbstractValidationHandler<UpdateTaskRequest, Validator>{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskUpdateHandler(Validator validator, TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        super(UpdateTaskRequest.class, validator);
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public Mono<ServerResponse> updateTask(ServerRequest request) {
        return request.bodyToMono(UpdateTaskRequest.class)
                .flatMap(update -> Mono.just(taskMapper.requestToTask(request.pathVariable("id"), validateEntity(update))))
                .flatMap(task -> {
                    return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                            .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found")))
                            .flatMap(existedTask -> {
                                Utils.copyNonNullValues(task, existedTask);
                                existedTask.setUpdatedAt(Instant.now());
                                return taskRepository.save(existedTask);
                            })
                            .flatMap(updatedTask -> getFullTask(updatedTask))
                            .flatMap(updatedTask -> Mono.just(taskMapper.taskToResponse(updatedTask))), TaskResponse.class);
                });
    }

    private Mono<Task> getFullTask(Task task) {
        Mono<User> author = userRepository.findById(task.getAuthorId());
        Mono<User> assignee = userRepository.findById(task.getAssigneeId());
        Mono<Set<User>> observers = userRepository.findAllByIdIn(task.getObserverIds()).collect(Collectors.toSet());

        return Mono.zip(author, assignee, observers)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .map(tuple -> {
                    User athr = tuple.getT1();
                    task.setAuthor(athr);
                    User asgn = tuple.getT2();
                    task.setAssignee(asgn);
                    Set<User> susr = tuple.getT3();
                    task.setObservers(susr);
                    return task;
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")));
    }
}
