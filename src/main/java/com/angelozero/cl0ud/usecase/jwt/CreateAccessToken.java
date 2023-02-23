package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.config.JwtPropertiesConfig;
import com.angelozero.cl0ud.entrypoint.jwt.rest.response.TokenResponse;
import com.angelozero.cl0ud.usecase.jwt.model.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CreateAccessToken {

    private final JwtPropertiesConfig jwtProps;

    @Autowired
    private Algorithm algorithm;

    @PostConstruct
    private void init() {
        jwtProps.setSecretKey(Base64.getEncoder().encodeToString(jwtProps.getSecretKey().getBytes()));
        algorithm = Algorithm.HMAC256(jwtProps.getSecretKey().getBytes());
    }

    public Token execute(String userName, List<String> roles) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = LocalDateTime.now().plusSeconds(jwtProps.getMilliseconds());
        String accessToken = getAccessToken(userName, roles, now, validity);
        String refreshToken = getRefreshToken(userName, roles, now);

        return Token.builder()
                .userName(userName)
                .authenticated(Boolean.TRUE)
                .created(now)
                .expiration(validity)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    private String getAccessToken(String userName, List<String> roles, LocalDateTime now, LocalDateTime validity) {

        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return JWT
                .create()
                .withClaim("roles", roles)
                .withIssuedAt(now.atZone(ZoneId.systemDefault()).toInstant())
                .withExpiresAt(validity.atZone(ZoneId.systemDefault()).toInstant())
                .withSubject(userName)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    private String getRefreshToken(String userName, List<String> roles, LocalDateTime now) {

        LocalDateTime validityRefreshToken = LocalDateTime.now().plusSeconds(jwtProps.getHours());

        return JWT
                .create()
                .withClaim("roles", roles)
                .withIssuedAt(now.atZone(ZoneId.systemDefault()).toInstant())
                .withExpiresAt(validityRefreshToken.atZone(ZoneId.systemDefault()).toInstant())
                .withSubject(userName)
                .sign(algorithm)
                .strip();
    }
}
