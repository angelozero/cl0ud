package com.angelozero.cl0ud.auth_jwt.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class JsonUtilsException extends RuntimeException {
    public JsonUtilsException(String message) {
        super("[Json Utils] - ".concat(message));
    }
}
