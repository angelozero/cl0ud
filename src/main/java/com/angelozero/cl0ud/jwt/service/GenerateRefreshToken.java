package com.angelozero.cl0ud.jwt.service;

import com.angelozero.cl0ud.jwt.gateway.RefreshTokenRepository;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.jwt.service.dao.User;
import com.angelozero.cl0ud.jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.jwt.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenerateRefreshToken {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RefreshTokenMapper refreshTokenMapper;

    private static final int ONE_HOUR = 3600000;

    public TokenRefreshed execute(String email) {

        User user = userMapper.toModel(userRepository.findUserByEmail(email));

        TokenRefreshed tokenRefreshed = TokenRefreshed.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(ONE_HOUR))
                .user(user)
                .build();

        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.save(refreshTokenMapper.toEntity(tokenRefreshed));

        return refreshTokenMapper.toModel(refreshTokenEntity);
    }
}
