package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.exception.person.GetAllPagedPersonsByNameException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class GetPagedPersonsByName {

    private final DataBaseGateway dataBaseGateway;
    private final PersonMapper personMapper;

    public Page<Person> execute(String name, Pageable pageable) {
        log.info("\n[GET_ALL_PERSONS_PAGED_BY_NAME] - Get a list of paged persons by name: {}\n", name);
        try {
            Page<PersonEntity> pagedEntityPersons = dataBaseGateway.getPagedPersonsEntityByName(name, pageable);
            return pagedEntityPersons.map(personMapper::toModel);

        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to get all paged persons by name: {}\n", name);
            throw new GetAllPagedPersonsByNameException("Error to get all paged persons by name: " + name + " - "+ ex.getMessage());
        }
    }
}
