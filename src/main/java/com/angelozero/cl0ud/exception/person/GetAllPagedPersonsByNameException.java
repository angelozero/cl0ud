package com.angelozero.cl0ud.exception.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GetAllPagedPersonsByNameException extends ZPersonException {
    public GetAllPagedPersonsByNameException(String message) {
        super("[Get All Paged Persons By Name Service] - ".concat(message));
    }
}
