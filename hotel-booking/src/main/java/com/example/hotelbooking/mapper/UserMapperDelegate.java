package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entities.User;
import com.example.hotelbooking.web.model.UserResponse;

public abstract class UserMapperDelegate implements UserMapper {
    @Override
    public UserResponse userToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserRole(user.getRoles().toString());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}
