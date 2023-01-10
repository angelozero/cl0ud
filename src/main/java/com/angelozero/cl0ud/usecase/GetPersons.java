package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GetPersons {

    public List<Person> execute() {
        log.info("[CLOUD-APP] - Get a list of persons");
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
