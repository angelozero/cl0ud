package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.service.dao.Authentication;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccessByRefreshToken {

    private final FindRefreshTokenByToken findRefreshTokenByToken;
    private final GenerateToken generateToken;
    private final RefreshTokenMapper mapper;

    public Authentication execute(String token) {

        TokenRefreshed tokenRefreshed = findRefreshTokenByToken.execute(token);
        String jwtToken = generateToken.execute(mapper.toEntity(tokenRefreshed).getUser());

        return Authentication.builder()
                .token(jwtToken)
                .build();
    }
}
