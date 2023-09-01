package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindRefreshTokenByToken {

    private final TokenGateway tokenGateway;
    private final VerifyExpiryRefreshTokenDate verifyExpiryRefreshTokenDate;
    private final RefreshTokenMapper mapper;

    public TokenRefreshed execute(String token) {

        TokenRefreshed tokenRefreshed;

        try {
            log.info("\n[FIND_REFRESH_TOKEN_BY_TOKEN] - Find refresh token by token: {}\n", token);
            tokenRefreshed = mapper.toModel(tokenGateway.findByToken(token));

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to find refresh token by token -  {}\n", ex.getMessage());
            throw new JwtException("TokenRefreshed: Error to find token by token - " + ex.getMessage());
        }

        verifyExpiryRefreshTokenDate.execute(tokenRefreshed);
        return tokenRefreshed;
    }
}
