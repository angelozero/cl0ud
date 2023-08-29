package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.exception.jwt.JwtValidationException;
import com.angelozero.cl0ud.auth_jwt.gateway.RefreshTokenRepository;
import com.angelozero.cl0ud.auth_jwt.service.dao.Authentication;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
