package com.angelozero.cl0ud;

import com.angelozero.cl0ud.exception.BadRequestException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {


    @GetMapping("/test")
    public Obj getSuccess() {
        return Obj.builder()
                .id(UUID.randomUUID().hashCode())
                .content(UUID.randomUUID().toString()).build();
    }

    @GetMapping("/internal")
    public Obj getInternalServerError() throws Exception {
        throw new Exception("Internal Server Error Test");
    }

    @GetMapping("/bad-request")
    public Obj getBadRequest() {
        throw new BadRequestException("Bad Request Exception Test");
    }

    /**
     * Obj class example
     */
    @Builder
    @Getter
    public static class Obj {
        private final int id;
        private final String content;
    }
}
