package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.exception.jwt.BearerTokenJwtAuthException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class ValidateToken {

    private final DecodedToken decodedToken;

    public boolean execute(String token) throws BearerTokenJwtAuthException {

        DecodedJWT decodedJWT = decodedToken.execute(token);

        try {
            return decodedJWT.getExpiresAt().before(new Date());

        } catch (Exception ex) {
            throw new BearerTokenJwtAuthException("Expired or invalid jwt token: " + ex.getMessage());
        }
    }
}
