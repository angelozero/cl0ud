package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.gateway.Cl0udGateway;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GetAllPersons {

    private final Cl0udGateway dataBaseGateway;

    public List<Person> execute() {
        log.info("[CLOUD-APP] - Get a list of persons");

        try {
            return dataBaseGateway.getAllPersonsEntity().stream().map(entity -> Person.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .age(entity.getAge())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return Collections.singletonList(Person.builder().name("Wrong person sorry :/").build());
        }
    }
}
