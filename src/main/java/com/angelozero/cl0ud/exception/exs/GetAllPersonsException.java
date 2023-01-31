package com.angelozero.cl0ud.exception.exs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GetAllPersonsException extends ZPersonException {
    public GetAllPersonsException(String message) {
        super("[Get All Persons Service] - ".concat(message));
    }
}
