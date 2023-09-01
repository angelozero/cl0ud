package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.mapper.RefreshTokenMapper;
import com.angelozero.cl0ud.auth_jwt.utils.JwtJsonUtils;
import com.angelozero.cl0ud.exception.jwt.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteToken {

    private final TokenGateway tokenGateway;
    private final RefreshTokenMapper mapper;

    public void execute(TokenRefreshed tokenRefreshed) {

        try {
            log.info("\n[DELETE_TOKEN] - Deleting token: {}\n", JwtJsonUtils.generateJson(tokenRefreshed));
            tokenGateway.delete(mapper.toEntity(tokenRefreshed));

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to delete token - {}\n", ex.getMessage());
            throw new JwtException("DeleteToken: Error to delete token - " + ex.getMessage());
        }

    }
}
