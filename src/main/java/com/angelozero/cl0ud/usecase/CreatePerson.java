package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.person.CreatePersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.usecase.utils.ExceptionMessage;
import com.angelozero.cl0ud.usecase.utils.LogMessage;
import com.angelozero.cl0ud.usecase.utils.ToJson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CreatePerson {

    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public Person execute(Person person) {
        log.info("\n[CREATING_PERSON] - Creating a person: {}\n", ToJson.execute(person));
        Optional.ofNullable(person)
                .orElseThrow(()
                        -> new CreatePersonException("Person Data is null"));

        try {
            PersonEntity personEntity = dataBaseGateway.savePerson(personMapper.toEntity(person));
            return personMapper.toModel(personEntity);

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to create a person\n");
            throw new CreatePersonException("Error to create a person: " + ex.getMessage());
        }
    }
}
