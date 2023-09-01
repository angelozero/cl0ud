package com.angelozero.cl0ud.auth_jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtConfigurationException extends ZJwtException {
    public JwtConfigurationException(String message) {
        super("[Jwt Configuration] - ".concat(message));
    }
}
