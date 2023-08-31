package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.auth_jwt.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenerateRefreshToken {


    private final TokenGateway tokenGateway;
    private final UserMapper userMapper;
    private final RefreshTokenMapper refreshTokenMapper;

    private static final int ONE_HOUR = 3600000;

    public TokenRefreshed execute(String email) {

        User user = userMapper.toModel(tokenGateway.findUserByEmail(email));

        TokenRefreshed tokenRefreshed = TokenRefreshed.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(ONE_HOUR))
                .user(user)
                .build();

        RefreshTokenEntity refreshTokenEntity = tokenGateway.save(refreshTokenMapper.toEntity(tokenRefreshed));

        return refreshTokenMapper.toModel(refreshTokenEntity);
    }
}
