package com.angelozero.cl0ud.auth_jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtDeleteTokenException extends ZJwtException {
    public JwtDeleteTokenException(String message) {
        super("[AUTH_JWT_DELETE_TOKEN - ERROR] - ".concat(message));
    }
}
