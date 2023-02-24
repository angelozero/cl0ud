package com.angelozero.cl0ud.entrypoint.jwt.mapper;

import com.angelozero.cl0ud.entrypoint.jwt.rest.response.TokenResponse;
import com.angelozero.cl0ud.entrypoint.jwt.rest.response.UserResponse;
import com.angelozero.cl0ud.usecase.jwt.model.Token;
import com.angelozero.cl0ud.usecase.jwt.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    UserResponse toResponse(User user);

    TokenResponse toResponse(Token token);
}
