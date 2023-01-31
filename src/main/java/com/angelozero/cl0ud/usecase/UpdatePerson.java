package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.exception.exs.UpdatePersonException;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UpdatePerson {

    private final GetPersonById getPersonById;
    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public void execute(Person person) {
        log.info("[UPDATE_PERSON] - Updating a person: {}", person);

        Optional.ofNullable(person)
                .map(Person::getId)
                .orElseThrow(() -> new UpdatePersonException("Person Data and/or Person ID is null"));

        try {
            if (getPersonById.execute(person.getId()) != null) {
                PersonEntity personEntity = dataBaseGateway.updatePerson(personMapper.toEntity(person));
                personMapper.toModel(personEntity);
            }

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to update a person\n");
            throw new UpdatePersonException("[ERROR] - Error to update a person: ");
        }

    }
}
