package com.angelozero.cl0ud.usecase.jwt.mapper;

import com.angelozero.cl0ud.gateway.postgressql.entity.jwt.UserEntity;
import com.angelozero.cl0ud.usecase.jwt.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserEntity userEntity);
}
