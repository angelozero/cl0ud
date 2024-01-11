package com.angelozero.cl0ud.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsyncPerson {

    private Long id;
    private String name;
    private int age;
    private AsyncMessage message;
}
