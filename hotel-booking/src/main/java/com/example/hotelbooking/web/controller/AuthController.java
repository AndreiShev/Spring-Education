package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.entities.RoleType;
import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.entities.UserRole;
import com.example.hotelbooking.mapper.UserMapper;
import com.example.hotelbooking.model.AuthEvent;
import com.example.hotelbooking.services.UserService;
import com.example.hotelbooking.web.model.UpsertUserRequest;
import com.example.hotelbooking.web.model.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    private final UserMapper userMapper;

    private final KafkaTemplate<String, AuthEvent> kafkaTemplate;

    @Value("${app.kafka.kafkaAuthTopic}")
    private String topicName;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UpsertUserRequest request,
                                                   @RequestParam(name = "roleType") RoleType roleType) {
        User user = userService.save(userMapper.requestToUser(request), UserRole.from(roleType));
        kafkaTemplate.send(topicName, getAuthEvent(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(
            userMapper.userToResponse(user)
        );
    }

    private AuthEvent getAuthEvent(User user) {
        AuthEvent authEvent = new AuthEvent();
        authEvent.setUserId(user.getId());
        return authEvent;
    }
}
