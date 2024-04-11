package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.web.model.UpsertUserRequest;
import com.example.hotelbooking.web.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    UpsertUserRequest userToUpsertRequest(User user);
}
