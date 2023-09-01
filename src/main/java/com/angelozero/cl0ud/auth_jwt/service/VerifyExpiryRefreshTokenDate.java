package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.utils.JwtJsonUtils;
import com.angelozero.cl0ud.auth_jwt.exception.JwtDeleteTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyExpiryRefreshTokenDate {

    private final DeleteToken deleteToken;

    public void execute(TokenRefreshed tokenRefreshed) {

        log.info("\n[VERIFY_EXPIRY_REFRESH_TOKEN_DATE] - Verifying refresh token date: {}\n", JwtJsonUtils.generateJson(tokenRefreshed));
        if (tokenRefreshed.getExpiryDate().compareTo(Instant.now()) < 0) {
            deleteToken.execute(tokenRefreshed);
            log.error("\n[ERROR] - Refresh token is expired: {}\n", JwtJsonUtils.generateJson(tokenRefreshed));
            throw new JwtDeleteTokenException("Refresh Token is expired");
        }
    }
}
