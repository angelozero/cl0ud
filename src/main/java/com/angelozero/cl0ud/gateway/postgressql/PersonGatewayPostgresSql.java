package com.angelozero.cl0ud.gateway.postgressql;

import com.angelozero.cl0ud.gateway.Cl0udGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.gateway.repository.Cl0udDataBaseRepository;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class PersonGatewayPostgresSql implements Cl0udGateway {

    private final Cl0udDataBaseRepository repository;

    @Override
    public PersonEntity savePerson(Person person) {
        log.info("[CLOUD-APP][GATEWAY] - Creating a person: {}", person);
        return repository.saveAndFlush(PersonEntity.builder()
                .name(person.getName())
                .age(person.getAge())
                .build());
    }

    @Override
    public void deletePersonEntityById(Long id) {

    }

    @Override
    public PersonEntity findPersonEntityById(Long id) {
        return null;
    }

    @Override
    public List<PersonEntity> getAllPersonsEntity() {
        log.info("[CLOUD-APP][GATEWAY] - Getting all persons entity");
        return repository.findAll();
    }
}
