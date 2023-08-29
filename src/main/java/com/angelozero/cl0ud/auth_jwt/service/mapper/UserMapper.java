package com.angelozero.cl0ud.auth_jwt.service.mapper;

import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", qualifiedByName = "role")
    UserEntity toEntity(User user);

    User toModel(UserEntity userEntity);

    @Named("role")
    default String setRole(String role) {
        return !StringUtils.isEmpty(role) ? role.toUpperCase() : StringUtils.EMPTY;
    }

}
