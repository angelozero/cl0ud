package com.angelozero.cl0ud.exception;


import com.angelozero.cl0ud.exception.jwt.ZJwtException;
import com.angelozero.cl0ud.exception.model.ExceptionModelResponse;
import com.angelozero.cl0ud.exception.person.ZPersonException;
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
    public final ResponseEntity<ExceptionModelResponse> handlePersonException(Exception ex, WebRequest request) {
        ExceptionModelResponse exceptionModelResponse = generateExceptionModelResponse(ex, request.getDescription(DONT_INCLUDE_CLIENT_INFO));

        return new ResponseEntity<>(exceptionModelResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ZJwtException.class)
    public final ResponseEntity<ExceptionModelResponse> handleJwtException(Exception ex, WebRequest request) {
        ExceptionModelResponse exceptionModelResponse = generateExceptionModelResponse(ex, request.getDescription(DONT_INCLUDE_CLIENT_INFO));

        return new ResponseEntity<>(exceptionModelResponse, HttpStatus.UNAUTHORIZED);
    }


    private ExceptionModelResponse generateExceptionModelResponse(Exception ex, String description) {
        return ExceptionModelResponse.builder()
                .date(LocalDateTime.now())
                .message(ex.getMessage())
                .details(description)
                .build();
    }
}
