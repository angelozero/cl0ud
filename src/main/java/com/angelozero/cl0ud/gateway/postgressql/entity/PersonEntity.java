package com.angelozero.cl0ud.gateway.postgressql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person", schema = "cl0ud")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

}
