package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.entities.RoleType;
import com.example.hotelbooking.entities.UserRole;
import com.example.hotelbooking.mapper.UserMapper;
import com.example.hotelbooking.services.UserService;
import com.example.hotelbooking.web.model.UpsertUserRequest;
import com.example.hotelbooking.web.model.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UpsertUserRequest request, @RequestParam(name = "roleType") RoleType roleType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            userMapper.userToResponse(userService.save(userMapper.requestToUser(request), UserRole.from(roleType)))
        );
    }
}
