package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.person.GetPersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
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
        log.info(LogMessage.INFO_GET_PERSON_BY_ID, id);
        Optional.ofNullable(id)
                .orElseThrow(()
                        -> new GetPersonException("ID is null"));

        try {
            return dataBaseGateway.findPersonEntityById(id).map(personMapper::toModel).orElse(null);

        } catch (Exception ex) {
            log.error(LogMessage.ERROR_GET_PERSON_BY_ID);
            throw new GetPersonException("Error to find a person by ID: " + ex.getMessage());
        }
    }
}

