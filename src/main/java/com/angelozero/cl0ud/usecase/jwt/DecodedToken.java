package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.config.JwtPropertiesConfig;
import com.angelozero.cl0ud.exception.jwt.BearerTokenJwtAuthException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DecodedToken {

    private final JwtPropertiesConfig jwtProps;

    public DecodedJWT execute(String accessToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProps.getSecretKey().getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            return jwtVerifier.verify(accessToken);

        } catch (Exception ex) {
            throw new BearerTokenJwtAuthException("Could not found the Bearer token in the request: " + ex.getMessage());
        }
    }
}
