package com.example.tasktracker.handler;

import com.example.tasktracker.dto.UpsertUserRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exceptions.EntityNotFoundException;
import com.example.tasktracker.mapper.UserMapper;
import com.example.tasktracker.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.example.tasktracker.utils.Utils.copyNonNullValues;


@Component
public class UserHandler extends AbstractValidationHandler<UpsertUserRequest, Validator> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserHandler(@Autowired Validator validator,
                       UserRepository userRepository,
                       UserMapper userMapper) {
        super(UpsertUserRequest.class, validator);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Mono<ServerResponse> getAllItem(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findAll(), User.class);
    }


    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok()
                .body(userRepository.findById(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found")))
                        .map(user -> userMapper.userToResponse(user)), UserResponse.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpsertUserRequest.class)
                .flatMap(userRequest -> Mono.just(userMapper.requestToUser(validateEntity(userRequest))))
                .flatMap(user -> {
                    user.setId(UUID.randomUUID().toString());
                    return ServerResponse.ok().body(userRepository.save(user).map(usr -> userMapper.userToResponse(usr)), UserResponse.class);
                });
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpsertUserRequest.class)
                .flatMap(userRequest -> Mono.just(userMapper.requestToUser(serverRequest.pathVariable("id"), userRequest)))
                .flatMap(user -> {
                    Mono<User> updatedUser = userRepository.findById(serverRequest.pathVariable("id"))
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                            .flatMap(currentUser -> {
                                copyNonNullValues(user, currentUser);
                                return userRepository.save(currentUser);
                            });

                    return ServerResponse.ok().body(updatedUser.map(usr -> userMapper.userToResponse(usr)), UserResponse.class);
                });
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        userRepository.deleteById(request.pathVariable("id")).subscribe();
        return ServerResponse.noContent().build();
    }

    @Override
    @SneakyThrows
    public UpsertUserRequest validateEntity(UpsertUserRequest body) {
        return super.validateEntity(body);
    }
}
