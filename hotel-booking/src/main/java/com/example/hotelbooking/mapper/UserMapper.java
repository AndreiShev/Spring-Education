package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.entities.UserRole;
import com.example.hotelbooking.web.model.UpsertUserRequest;
import com.example.hotelbooking.web.model.UserResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = "spring",  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User requestToUser(UpsertUserRequest request);

    UserResponse userToResponse(User user);

    UpsertUserRequest userToUpsertRequest(User user);

    default String map(UserRole value) {
        return value.getAuthority().name();
    }
}
