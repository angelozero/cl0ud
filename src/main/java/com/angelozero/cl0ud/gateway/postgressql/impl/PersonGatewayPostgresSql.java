package com.angelozero.cl0ud.gateway.postgressql.impl;

import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.gateway.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PersonGatewayPostgresSql implements DataBaseGateway {

    private final PersonRepository repository;

    @Override
    public PersonEntity savePerson(PersonEntity personEntity) {
        return repository.saveAndFlush(personEntity);
    }

    @Override
    public PersonEntity updatePerson(PersonEntity personEntity) {
        return repository.saveAndFlush(personEntity);
    }

    @Override
    public void deletePersonEntityById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<PersonEntity> findPersonEntityById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PersonEntity> getAllPersonsEntity() {
        return repository.findAll();
    }

    @Override
    public Page<PersonEntity> getPagedPersonsEntity(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
