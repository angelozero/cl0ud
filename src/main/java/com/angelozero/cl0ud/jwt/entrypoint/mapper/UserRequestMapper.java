package com.angelozero.cl0ud.jwt.entrypoint.mapper;

import com.angelozero.cl0ud.jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.jwt.service.dao.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    User toUser(RegisterRequest request);

}
