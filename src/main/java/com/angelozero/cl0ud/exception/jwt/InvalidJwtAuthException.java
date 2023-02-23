package com.angelozero.cl0ud.exception.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthException extends ZJwtException {
    public InvalidJwtAuthException(String message) {
        super("[User Auth Service] - ".concat(message));
    }
}
