package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class GetPersons {

    public List<Person> execute() {
        return Arrays.asList(
                Person.builder()
                        .age(32)
                        .name("Angelo")
                        .build(),

                Person.builder()
                        .age(12)
                        .name("Jake")
                        .build(),

                Person.builder()
                        .age(3)
                        .name("Xeroques")
                        .build()
        );
    }
}
