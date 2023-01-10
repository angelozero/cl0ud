package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetPersonById {

    public Person execute(int id) {
        return id == 32 ? generatePerson(32, "Angelo") : generatePerson(12, "Jake");
    }

    private Person generatePerson(int age, String Angelo) {
        return Person.builder()
                .age(age)
                .name(Angelo)
                .build();
    }
}
