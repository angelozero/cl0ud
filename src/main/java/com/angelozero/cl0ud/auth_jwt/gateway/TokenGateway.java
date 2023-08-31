package com.angelozero.cl0ud.auth_jwt.gateway;

import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;

public interface TokenGateway {

    UserEntity findUserByEmail(String email);

    RefreshTokenEntity findByToken(String token);

    RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity);

    void delete(RefreshTokenEntity refreshTokenEntity);

    void save(UserEntity userEntity);
}
