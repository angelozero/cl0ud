package com.angelozero.cl0ud.usecase.person.mapper;

import com.angelozero.cl0ud.gateway.postgressql.entity.dao.PersonEntity;
import com.angelozero.cl0ud.usecase.person.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toModel(PersonEntity personEntity);

    List<Person> toModelList(List<PersonEntity> personListEntity);

    PersonEntity toEntity(Person person);
}
