package com.angelozero.cl0ud.exception.exs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreatePersonException extends ZPersonException {
    public CreatePersonException(String message) {
        super("[Create Person Service] - ".concat(message));
    }
}
