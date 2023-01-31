package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CreatePerson {

    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public Person execute(Person person) {
        log.info("[CLOUD-APP] - Creating a person: {}", person);
        try {
            PersonEntity personEntity = dataBaseGateway.savePerson(personMapper.toEntity(person));
            return personMapper.toModel(personEntity);

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to create a person\n");
            throw new RuntimeException("[ERROR] - Error to create a person");
        }
    }
}
