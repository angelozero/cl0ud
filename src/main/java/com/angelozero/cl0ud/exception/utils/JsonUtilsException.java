package com.angelozero.cl0ud.exception.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class JsonUtilsException extends RuntimeException {
    public JsonUtilsException(String message) {
        super("[Json Utils] - ".concat(message));
    }
}
