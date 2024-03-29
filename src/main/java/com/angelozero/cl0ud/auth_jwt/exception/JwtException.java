package com.angelozero.cl0ud.auth_jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtException extends ZJwtException {
    public JwtException(String message) {
        super("[AUTH_JWT - ERROR] - ".concat(message));
    }
}
