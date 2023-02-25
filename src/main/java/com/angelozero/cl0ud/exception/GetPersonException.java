package com.angelozero.cl0ud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GetPersonException extends ZPersonException {
    public GetPersonException(String message) {
        super("[Get Person Service] - ".concat(message));
    }
}
