package com.angelozero.cl0ud.exception.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtUserNotFoundException extends ZJwtException {
    public JwtUserNotFoundException(String message) {
        super("[AUTH_JWT_USER_NOT_FOUND - ERROR] - ".concat(message));
    }
}
