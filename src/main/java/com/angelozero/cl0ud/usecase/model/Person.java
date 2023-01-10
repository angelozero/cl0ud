package com.angelozero.cl0ud.usecase.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Person {

    private String name;
    private int age;
}
