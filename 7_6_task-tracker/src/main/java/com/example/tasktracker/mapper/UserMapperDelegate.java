package com.example.tasktracker.mapper;

import com.example.tasktracker.dto.UpsertUserRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class UserMapperDelegate implements UserMapper {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<UserResponse> getUsersResponse(Set<User> setUsers) {
        return setUsers.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toSet());
    }
}
