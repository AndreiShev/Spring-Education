package com.example.rest.mapper;

import com.example.rest.model.Role;
import com.example.rest.web.model.RoleResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(RoleMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy =  ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleResponse RoleToResponse(Role role);

}
