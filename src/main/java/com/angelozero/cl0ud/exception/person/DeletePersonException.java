package com.angelozero.cl0ud.exception.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeletePersonException extends ZPersonException {
    public DeletePersonException(String message) {
        super("[Delete Person Service] - ".concat(message));
    }
}
