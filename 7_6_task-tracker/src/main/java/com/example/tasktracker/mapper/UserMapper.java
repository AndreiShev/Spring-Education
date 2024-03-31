package com.example.tasktracker.mapper;

import com.example.tasktracker.dto.UpsertUserRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(String userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    default Set<User> getSetUsers(Set<UpsertUserRequest> setUsers) {
        return setUsers.stream()
                .map(upsert -> new User(UUID.randomUUID().toString(), upsert.getUsername(), upsert.getEmail(), upsert.getPassword()))
                .collect(Collectors.toSet());
    }

    Set<UserResponse> getUsersResponse(Set<User> setUsers);

}
