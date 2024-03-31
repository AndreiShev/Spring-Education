package com.example.tasktracker.handler;

import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.UpsertTaskRequest;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exceptions.EntityNotFoundException;
import com.example.tasktracker.mapper.TaskMapper;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class TaskHandler extends AbstractValidationHandler<UpsertTaskRequest, Validator> {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskHandler(Validator validator,
                       TaskRepository taskRepository,
                       UserRepository userRepository,
                       TaskMapper taskMapper) {
        super(UpsertTaskRequest.class, validator);
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> getAllTask(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findAll(), Task.class);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> getTaskById(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found")))
                        .flatMap(task -> getFullTask(task))
                        .flatMap(task -> Mono.just(taskMapper.taskToResponse(task))), TaskResponse.class);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> createTask(ServerRequest request) {
        return request.bodyToMono(UpsertTaskRequest.class)
                .flatMap(upsertTaskRequest -> Mono.just(taskMapper.requestToTask(validateEntity(upsertTaskRequest))))
                .flatMap(task -> {
                    task.setId(UUID.randomUUID().toString());
                    task.setCreatedAt(Instant.now());
                    task.setStatus(TaskStatus.TODO);
                    userRepository.findById(task.getAuthorId())
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")));
                    userRepository.findById(task.getAssigneeId())
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignee not found")));
                    return Mono.just(task);
                })
                .flatMap(task -> getFullTask(task))
                .flatMap(taskRepository::save)
                .flatMap(task -> {
                    return ServerResponse.ok().body(Mono.just(taskMapper.taskToResponse(task)), TaskResponse.class);
                });

    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> changeAuthor(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found")))
                .map(task -> {
                    userRepository.findById(request.pathVariable("authorId"))
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")));
                    task.setAuthorId(request.pathVariable("authorId"));
                    return task;
                })
                .flatMap(taskRepository::save)
                .flatMap(task -> getFullTask(task))
                .flatMap(task -> Mono.just(taskMapper.taskToResponse(task))), TaskResponse.class);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> changeAssignee(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found")))
                .map(task -> {
                    userRepository.findById(request.pathVariable("assigneeId"))
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignee not found")));
                    task.setAssigneeId(request.pathVariable("assigneeId"));
                    return task;
                })
                .flatMap(task -> getFullTask(task))
                .flatMap(taskRepository::save)
                .flatMap(task -> Mono.just(taskMapper.taskToResponse(task))), TaskResponse.class);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> addObserver(ServerRequest request) {
        return ServerResponse.ok().body(taskRepository.findById(request.pathVariable("id"))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found")))
                .map(task -> {
                    Set<String> observersId = task.getObserverIds();
                    userRepository.findById(request.pathVariable("observerId"))
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Observer not found")));
                    task.setObserverIds(observersId);
                    return task;
                })
                .flatMap(task -> getFullTask(task))
                .flatMap(taskRepository::save)
                .flatMap(task -> Mono.just(taskMapper.taskToResponse(task))), TaskResponse.class);

    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> deleteTask(ServerRequest request) {
        taskRepository.deleteById(request.pathVariable("id")).subscribe();
        return ServerResponse.noContent().build();
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
                    });
    }
}
