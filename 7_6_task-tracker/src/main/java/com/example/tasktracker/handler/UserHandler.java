package com.example.tasktracker.handler;

import com.example.tasktracker.dto.UpsertUserRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.mapper.UserMapper;
import com.example.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.example.tasktracker.utils.Utils.copyNonNullValues;


@Component
@RequiredArgsConstructor
public class UserHandler {
    /**
     * Для сущности User необходимо создать API, который предоставляет возможность:
     *
     * найти всех пользователей,
     * найти пользователя по ID,
     * создать пользователя,
     * обновить информацию о пользователе,
     * удалить пользователя по ID.*/
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<ServerResponse> getAllItem(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findAll(), User.class);
    }


    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userRepository.findById(request.pathVariable("id"))), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpsertUserRequest.class)
                .flatMap(userRequest -> Mono.just(userMapper.requestToUser(userRequest)))
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
}
