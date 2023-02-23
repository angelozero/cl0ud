package com.angelozero.cl0ud.gateway.postgressql.impl;

import com.angelozero.cl0ud.gateway.JwtSecurityGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.jwt.UserEntity;
import com.angelozero.cl0ud.gateway.repository.jwt.JwtUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserGatewayPostgresSql implements JwtSecurityGateway {

    private final JwtUserRepository repository;


    @Override
    public UserEntity findByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
