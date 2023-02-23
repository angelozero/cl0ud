package com.angelozero.cl0ud.exception.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BearerTokenJwtAuthException extends ZJwtException {
    public BearerTokenJwtAuthException(String message) {
        super("[Bearer Token Service] - ".concat(message));
    }
}
