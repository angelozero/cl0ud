package com.angelozero.cl0ud.entrypoint.rest.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {

    private Long id;
    private String name;
    private int age;
}
