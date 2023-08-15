package com.angelozero.cl0ud.jwt.service;

import com.angelozero.cl0ud.exception.jwt.JwtValidationException;
import com.angelozero.cl0ud.jwt.gateway.RefreshTokenRepository;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.jwt.service.dao.Authentication;
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
public class UserAccessByRefreshToken {

    private final RefreshTokenRepository refreshTokenRepository;
    private final GenerateToken generateToken;
    private final RefreshTokenMapper mapper;

    public Authentication execute(String token) {

        TokenRefreshed tokenRefreshed = mapper.toModel(refreshTokenRepository.findByToken(token));
        verifyExpirationDate(tokenRefreshed);
        String jwtToken = generateToken.execute(mapper.toEntity(tokenRefreshed).getUser());

        return Authentication.builder()
                .token(jwtToken)
                .build();
    }


    private void verifyExpirationDate(TokenRefreshed tokenRefreshed) {
        if (tokenRefreshed.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(mapper.toEntity(tokenRefreshed));
            throw new JwtValidationException("Refresh Token is expired");
        }
    }
}
