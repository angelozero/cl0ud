package com.angelozero.cl0ud.auth_jwt.service.mapper;

import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface RefreshTokenMapper {


    TokenRefreshed toModel(RefreshTokenEntity refreshTokenEntity);

    RefreshTokenEntity toEntity(TokenRefreshed tokenRefreshed);
}
