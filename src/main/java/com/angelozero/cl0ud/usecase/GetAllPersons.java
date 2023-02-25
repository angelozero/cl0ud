package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.GetAllPersonsException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GetAllPersons {

    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public List<Person> execute() {
        log.info("[GET_ALL_PERSONS] - Get a list of persons");

        try {
            return personMapper.toModelList(dataBaseGateway.getAllPersonsEntity());

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to get all persons\n");
            throw new GetAllPersonsException("Error to get all persons: " + ex.getMessage());
        }
    }
}
