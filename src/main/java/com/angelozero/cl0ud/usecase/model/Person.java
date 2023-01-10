package com.angelozero.cl0ud.usecase.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person {

    private String name;
    private int age;
}
