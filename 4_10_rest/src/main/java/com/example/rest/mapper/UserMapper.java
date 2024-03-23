package com.example.rest.mapper;

import com.example.rest.model.User;
import com.example.rest.web.model.UpsertUserRequest;
import com.example.rest.web.model.UserListResponse;
import com.example.rest.web.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE, uses = {NewsMapper.class, RoleMapper.class})
public interface UserMapper {
    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse usersListToUserResponseList(List<User> users) {
        UserListResponse response = new UserListResponse();

        response.setUsers(users.stream()
                .map(this::userToResponse).collect(Collectors.toList()));

        return response;
    }
}
