package com.angelozero.cl0ud.auth_jwt.entrypoint.mapper;

import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.auth_jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.auth_jwt.enums.RoleEnum;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenData;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    @Mapping(target = "accountNonExpired", defaultValue = "true")
    @Mapping(target = "accountNonLocked", defaultValue = "true")
    @Mapping(target = "credentialsNonExpired", defaultValue = "true")
    @Mapping(target = "enable", defaultValue = "true")
    @Mapping(target = "role", defaultValue = "ADMIN", qualifiedByName = "role")
    User toUser(RegisterRequest request);

    AuthenticationResponse toAuthenticateResponse(TokenData tokenData);

    @Named("role")
    default String setRole(String role) {
        return RoleEnum.getRoleInfo(role);
    }
}
