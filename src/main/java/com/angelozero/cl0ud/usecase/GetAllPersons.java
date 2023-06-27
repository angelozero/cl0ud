package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.person.GetAllPagedPersonsException;
import com.angelozero.cl0ud.exception.person.GetAllPersonsException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GetAllPersons {

    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public List<Person> execute() {
        log.info("\n[GET_ALL_PERSONS] - Get a list of persons\n");

        try {
            return personMapper.toModelList(dataBaseGateway.getAllPersonsEntity());

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to get all persons\n");
            throw new GetAllPersonsException("Error to get all persons: " + ex.getMessage());
        }
    }

    public Page<Person> execute(Pageable pageable) {
        log.info("\n[GET_ALL_PERSONS_PAGED] - Get a list of paged persons\n");
        try {
            Page<PersonEntity> pagedEntityPersons = dataBaseGateway.getPagedPersonsEntity(PageRequest.of(0, 1));
            return pagedEntityPersons.map(personMapper::toModel);

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to get all paged persons\n");
            throw new GetAllPagedPersonsException("Error to get all paged persons: " + ex.getMessage());
        }
    }
}
