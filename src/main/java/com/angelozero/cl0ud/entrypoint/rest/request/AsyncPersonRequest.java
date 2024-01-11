package com.angelozero.cl0ud.entrypoint.rest.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsyncPersonRequest {

    private Long id;
    private String name;
    private int age;
    private AsyncMessageRequest message;
}
