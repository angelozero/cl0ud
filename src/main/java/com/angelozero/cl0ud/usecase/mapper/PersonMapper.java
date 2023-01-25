package com.angelozero.cl0ud.usecase.mapper;

import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toModel(PersonEntity personEntity);

    PersonEntity toEntity(Person person);
}
