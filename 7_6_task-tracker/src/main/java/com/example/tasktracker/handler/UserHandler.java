package com.example.tasktracker.handler;

import com.example.tasktracker.dto.UpsertUserRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.RoleType;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exceptions.EntityNotFoundException;
import com.example.tasktracker.mapper.UserMapper;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;




@Component
public class UserHandler extends AbstractValidationHandler<UpsertUserRequest, Validator> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserHandler(@Autowired Validator validator,
                       UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder, UserService userService) {
        super(UpsertUserRequest.class, validator);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> getAllItem(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findAll(), User.class);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
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
                    return ServerResponse.ok()
                            .body(userService.createNewAccount(user,
                                    serverRequest.queryParam("roleType").map(roleType -> RoleType.valueOf(roleType))
                                            .get())
                                    .map(usr -> userMapper.userToResponse(usr)), UserResponse.class);
                });
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpsertUserRequest.class)
                .flatMap(userRequest -> Mono.just(userMapper.requestToUser(serverRequest.pathVariable("id"), userRequest)))
                .flatMap(user -> {
                    Mono<User> updatedUser = userService.updateUser(user, serverRequest.pathVariable("id"));
                    return ServerResponse.ok().body(updatedUser.map(usr -> userMapper.userToResponse(usr)), UserResponse.class);
                });
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        userService.delete(request.pathVariable("id"));
        return ServerResponse.noContent().build();
    }

    @Override
    @SneakyThrows
    public UpsertUserRequest validateEntity(UpsertUserRequest body) {
        return super.validateEntity(body);
    }
}
