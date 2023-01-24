package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GetPersonById {

    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public Person execute(Long id) {
        log.info("[CLOUD-APP] - Getting a person by id: {}", id);
        try {
            return dataBaseGateway.findPersonEntityById(id).map(personMapper::toModel).orElse(null);

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to find a person by ID\n");
            throw new RuntimeException("[ERROR] - Error to find a person by ID");
        }
    }
}

