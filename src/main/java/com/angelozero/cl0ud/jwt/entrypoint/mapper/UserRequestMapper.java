package com.angelozero.cl0ud.jwt.entrypoint.mapper;

import com.angelozero.cl0ud.jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.jwt.service.dao.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "enable", constant = "true")
    @Mapping(target = "role", constant = "ADMIN")
    User toUser(RegisterRequest request);

}
