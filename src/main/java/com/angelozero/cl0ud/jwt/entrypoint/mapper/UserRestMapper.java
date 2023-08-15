package com.angelozero.cl0ud.jwt.entrypoint.mapper;

import com.angelozero.cl0ud.jwt.entrypoint.rest.AuthenticationResponse;
import com.angelozero.cl0ud.jwt.entrypoint.rest.RegisterRequest;
import com.angelozero.cl0ud.jwt.enums.RoleEnum;
import com.angelozero.cl0ud.jwt.service.dao.Authentication;
import com.angelozero.cl0ud.jwt.service.dao.User;
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

    AuthenticationResponse toAuthenticateResponse(Authentication authentication);

    @Named("role")
    default String setRole(String role) {
        return RoleEnum.getRoleInfo(role);
    }
}
