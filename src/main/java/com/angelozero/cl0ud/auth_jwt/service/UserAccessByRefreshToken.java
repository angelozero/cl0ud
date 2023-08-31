package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import com.angelozero.cl0ud.auth_jwt.service.dao.Authentication;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserAccessByRefreshToken {

    private final TokenGateway tokenGateway;
    private final GenerateToken generateToken;
    private final RefreshTokenMapper mapper;

    public Authentication execute(String token) {

        TokenRefreshed tokenRefreshed = mapper.toModel(tokenGateway.findByToken(token));
        verifyExpirationDate(tokenRefreshed);
        String jwtToken = generateToken.execute(mapper.toEntity(tokenRefreshed).getUser());

        return Authentication.builder()
                .token(jwtToken)
                .build();
    }


    private void verifyExpirationDate(TokenRefreshed tokenRefreshed) {
        if (tokenRefreshed.getExpiryDate().compareTo(Instant.now()) < 0) {
            tokenGateway.delete(mapper.toEntity(tokenRefreshed));
            throw new JwtException("Refresh Token is expired");
        }
    }
}
