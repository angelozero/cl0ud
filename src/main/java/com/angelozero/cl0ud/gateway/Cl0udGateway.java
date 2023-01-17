package com.angelozero.cl0ud.gateway;

import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;

public interface Cl0udGateway {

    PersonEntity savePerson(Person person);

//    void deletePersonEntityById(Long id);
//
//    PersonEntity findPersonEntityById(Long id);
//
//    List<PersonEntity> getAllPersonsEntity();
}
