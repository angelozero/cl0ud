package com.angelozero.cl0ud.exception;


import com.angelozero.cl0ud.exception.ZPersonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class Cl0udExceptionHandler extends ResponseEntityExceptionHandler {

    public static final boolean DONT_INCLUDE_CLIENT_INFO = false;
    public static final boolean INCLUDE_CLIENT_INFO = true;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionModelResponse> handleGenericException(Exception ex, WebRequest request) {
        ExceptionModelResponse exceptionModelResponse = generateExceptionModelResponse(ex, request.getDescription(DONT_INCLUDE_CLIENT_INFO));

        return new ResponseEntity<>(exceptionModelResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ZPersonException.class)
    public final ResponseEntity<ExceptionModelResponse> handlePersonExceptionException(Exception ex, WebRequest request) {
        ExceptionModelResponse exceptionModelResponse = generateExceptionModelResponse(ex, request.getDescription(DONT_INCLUDE_CLIENT_INFO));

        return new ResponseEntity<>(exceptionModelResponse, HttpStatus.BAD_REQUEST);
    }



    private ExceptionModelResponse generateExceptionModelResponse(Exception ex, String description) {
        return ExceptionModelResponse.builder()
                .date(LocalDateTime.now())
                .message(ex.getMessage())
                .details(description)
                .build();
    }
}
