package com.example.rest.web.controller;

import com.example.rest.mapper.UserMapper;
import com.example.rest.model.User;
import com.example.rest.services.UserService;
import com.example.rest.web.model.RequestGetAll;
import com.example.rest.web.model.UpsertUserRequest;
import com.example.rest.web.model.UserListResponse;
import com.example.rest.web.model.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "User API")
public class UserController {
    private final UserService userServiceImpl;

    private final UserMapper userMapper;


    @GetMapping
    public ResponseEntity<UserListResponse> findAll(@RequestBody @Valid RequestGetAll request) {
        return ResponseEntity.ok(
                userMapper.usersListToUserResponseList(
                        userServiceImpl.findAll(request.getPageNumber(), request.getLimit())
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
            userMapper.userToResponse(
                    userServiceImpl.findById(id)
            )
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = userServiceImpl.save(userMapper.requestToUser(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody @Valid UpsertUserRequest request) {
        User updatedUser = userServiceImpl.update(userMapper.requestToUser(userId, request));

        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
