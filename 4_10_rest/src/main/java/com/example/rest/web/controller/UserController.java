package com.example.rest.web.controller;

import com.example.rest.mapper.UserMapper;
import com.example.rest.model.Role;
import com.example.rest.model.RoleType;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "User API")
public class UserController {
    private final UserService userServiceImpl;

    private final UserMapper userMapper;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserListResponse> findAll(@RequestBody @Valid RequestGetAll request) {
        return ResponseEntity.ok(
                userMapper.usersListToUserResponseList(
                        userServiceImpl.findAll(request.getPageNumber(), request.getLimit())
                )
        );
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<UserResponse> findById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        return ResponseEntity.ok(
            userMapper.userToResponse(
                    userServiceImpl.findById(id)
            )
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request,
                                               @RequestParam(name = "roleType") RoleType roleType) {
        User newUser = userServiceImpl.save(userMapper.requestToUser(request), Role.from(roleType));

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody @Valid UpsertUserRequest request) {
        User updatedUser = userServiceImpl.update(userMapper.requestToUser(userId, request));

        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
