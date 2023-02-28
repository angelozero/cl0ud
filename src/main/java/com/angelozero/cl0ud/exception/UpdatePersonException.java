package com.angelozero.cl0ud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UpdatePersonException extends ZPersonException {
    public UpdatePersonException(String message) {
        super("[Update Person Service] - ".concat(message));
    }
}
