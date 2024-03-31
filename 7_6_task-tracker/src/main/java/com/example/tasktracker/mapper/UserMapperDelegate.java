package com.example.tasktracker.mapper;

import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class UserMapperDelegate implements UserMapper {

    @Override
    public Set<UserResponse> getUsersResponse(Set<User> setUsers) {
        return setUsers.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toSet());
    }
}
