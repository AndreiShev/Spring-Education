package com.example.rest.mapper;

import com.example.rest.model.Role;
import com.example.rest.web.model.RoleResponse;

public abstract class RoleMapperDelegate implements RoleMapper {
    @Override
    public RoleResponse RoleToResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setAuthority(role.getAuthority().name());
        return response;
    }
}
