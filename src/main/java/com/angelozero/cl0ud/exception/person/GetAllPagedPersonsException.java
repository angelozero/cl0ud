package com.angelozero.cl0ud.exception.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GetAllPagedPersonsException extends ZPersonException {
    public GetAllPagedPersonsException(String message) {
        super("[Get All Paged Persons Service] - ".concat(message));
    }
}
