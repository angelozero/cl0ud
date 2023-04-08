package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.exception.person.UpdatePersonException;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.usecase.utils.LogMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UpdatePerson {

    private final DataBaseGateway dataBaseGateway;
    private final GetPersonById getPersonById;
    private final PersonMapper personMapper;

    public void execute(Person person) {
        log.info(LogMessage.INFO_UPDATE_PERSON, person);

        Optional.ofNullable(person)
                .map(Person::getId)
                .orElseThrow(()
                        -> new UpdatePersonException("Person Data and/or Person ID is null"));

        try {
            if (getPersonById.execute(person.getId()) != null) {
                PersonEntity personEntity = dataBaseGateway.updatePerson(personMapper.toEntity(person));
                personMapper.toModel(personEntity);
            }

        } catch (Exception ex) {
            log.error(LogMessage.ERROR_UPDATE_PERSON);
            throw new UpdatePersonException("Error to update a person: " + ex.getMessage());
        }

    }
}
