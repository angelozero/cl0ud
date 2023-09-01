package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.auth_jwt.service.utils.JwtJsonUtils;
import com.angelozero.cl0ud.auth_jwt.exception.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveRefreshToken {

    private final TokenGateway tokenGateway;
    private final RefreshTokenMapper refreshTokenMapper;

    public TokenRefreshed execute(TokenRefreshed tokenRefreshed) {

        try {
            log.info("\n[SAVE_REFRESH_TOKEN] - Saving refresh token: {}\n", JwtJsonUtils.generateJson(tokenRefreshed));
            RefreshTokenEntity refreshTokenEntity = tokenGateway.save(refreshTokenMapper.toEntity(tokenRefreshed));
            return refreshTokenMapper.toModel(refreshTokenEntity);

        } catch (Exception ex) {
            log.error("\n[ERROR] - SaveRefreshToken: Error to save the refresh token - {}\n", ex.getMessage());
            throw new JwtException("SaveRefreshToken: Error to save the refresh token - " + ex.getMessage());
        }
    }
}
