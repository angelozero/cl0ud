package com.angelozero.cl0ud.gateway.postgressql;

import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.gateway.repository.Cl0udDataBaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PersonGatewayPostgresSql implements DataBaseGateway {

    private final Cl0udDataBaseRepository repository;

    @Override
    public PersonEntity savePerson(PersonEntity personEntity) {
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
}
