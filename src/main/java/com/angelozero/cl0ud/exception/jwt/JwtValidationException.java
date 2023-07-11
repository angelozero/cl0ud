package com.angelozero.cl0ud.exception.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtValidationException extends ZJwtException {
    public JwtValidationException(String message) {
        super("[Jwt Validation] - ".concat(message));
    }
}
