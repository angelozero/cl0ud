package com.angelozero.cl0ud.auth_jwt.service;

import com.angelozero.cl0ud.auth_jwt.service.dao.TokenRefreshed;
import com.angelozero.cl0ud.auth_jwt.service.dao.User;
import com.angelozero.cl0ud.auth_jwt.service.utils.JwtJsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateRefreshToken {

    private final FindUserByEmail findUserByEmail;
    private final SaveRefreshToken saveRefreshToken;

    private static final int ONE_HOUR = 3600000;

    public TokenRefreshed execute(String email) {

        User user = findUserByEmail.execute(email);

        TokenRefreshed tokenRefreshed = TokenRefreshed.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(ONE_HOUR))
                .user(user)
                .build();

        log.info("\n[GENERATE_REFRESH_TOKEN] - Generating refresh token: {}\n", JwtJsonUtils.generateJson(tokenRefreshed));
        return saveRefreshToken.execute(tokenRefreshed);
    }
}
