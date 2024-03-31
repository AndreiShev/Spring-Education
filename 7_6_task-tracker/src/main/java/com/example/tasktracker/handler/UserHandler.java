package com.example.tasktracker.handler;

import com.example.tasktracker.dto.UpsertUserRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.RoleType;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exceptions.EntityNotFoundException;
import com.example.tasktracker.mapper.UserMapper;
import com.example.tasktracker.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.example.tasktracker.utils.Utils.copyNonNullValues;


@Component
public class UserHandler extends AbstractValidationHandler<UpsertUserRequest, Validator> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserHandler(@Autowired Validator validator,
                       UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        super(UpsertUserRequest.class, validator);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ServerResponse> getAllItem(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findAll(), User.class);
    }


    //@PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
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
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    Set<RoleType> roles = new HashSet<>();
                    roles.add(serverRequest.queryParam("roleType").map(roleType -> RoleType.valueOf(roleType)).get());
                    user.setRoles(roles);
                    return ServerResponse.ok().body(userRepository.save(user).map(usr -> userMapper.userToResponse(usr)), UserResponse.class);
                });
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
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

    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MANAGER')")
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
