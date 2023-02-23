package com.angelozero.cl0ud.gateway;

import com.angelozero.cl0ud.gateway.postgressql.entity.jwt.UserEntity;

public interface JwtSecurityGateway {

    UserEntity findByUserName(String userName);
}
