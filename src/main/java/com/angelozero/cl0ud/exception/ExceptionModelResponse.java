package com.angelozero.cl0ud.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionModelResponse {

    private LocalDateTime date;
    private String message;
    private String details;
}
