package com.angelozero.cl0ud.entrypoint.rest.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "data")
public class PersonResponse {

    private Long id;
    private String name;
    private int age;
}
