package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GetAllPersons {

//    private final Cl0udGateway dataBaseGateway;

    public List<Person> execute() {
        log.info("[CLOUD-APP] - Get a list of persons");
        return null;
//        try {
//            return dataBaseGateway.getAllPersonsEntity().stream().map(entity -> Person.builder()
//                            .name(entity.getName())
//                            .age(entity.getAge())
//                            .build())
//                    .collect(Collectors.toList());
//        } catch (Exception ex) {
//            return Collections.singletonList(Person.builder().build());
//        }
    }
}
