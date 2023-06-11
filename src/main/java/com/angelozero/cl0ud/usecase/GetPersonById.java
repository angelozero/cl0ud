package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.person.GetPersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.usecase.utils.ExceptionMessage;
import com.angelozero.cl0ud.usecase.utils.LogMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class GetPersonById {

    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public Person execute(Long id) {
        log.info("\n[GET_PERSON_BY_ID] - Getting a person by id: {}\n", id);
        Optional.ofNullable(id)
                .orElseThrow(()
                        -> new GetPersonException("Person ID is null"));

        try {
            return dataBaseGateway.findPersonEntityById(id).map(personMapper::toModel).orElse(null);

        } catch (Exception ex) {
            log.error(LogMessage.ERROR_GET_PERSON_BY_ID);
            throw new GetPersonException(ExceptionMessage.ERROR_FIND_A_PERSON_BY_ID + ex.getMessage());
        }
    }
}

