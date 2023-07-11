package com.angelozero.cl0ud.jwt.service.mapper;

import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.jwt.service.dao.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", qualifiedByName = "role")
    UserEntity toEntity(User user);

    @Named("role")
    default String setRole(String role) {
        return role.toUpperCase();
    }

}
