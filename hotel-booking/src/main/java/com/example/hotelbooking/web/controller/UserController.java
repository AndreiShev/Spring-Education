package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.mapper.UserMapper;
import com.example.hotelbooking.services.UserService;
import com.example.hotelbooking.web.model.UpsertUserRequest;
import com.example.hotelbooking.web.model.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestParam UpsertUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            userMapper.userToResponse(userService.save(userMapper.requestToUser(request)))
        );
    }
}
