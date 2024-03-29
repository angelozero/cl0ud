package com.angelozero.cl0ud.gateway;

import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DataBaseGateway {

    PersonEntity savePerson(PersonEntity personEntity);

    PersonEntity updatePerson(PersonEntity personEntity);

    void deletePersonEntityById(Long id);

    Optional<PersonEntity> findPersonEntityById(Long id);

    List<PersonEntity> getAllPersonsEntity();

    Page<PersonEntity> getPagedPersonsEntity(Pageable pageable);

    Page<PersonEntity> getPagedPersonsEntityByName(String name, Pageable pageable);
}
