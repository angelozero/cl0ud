package com.angelozero.cl0ud.entrypoint.person.mapper;

import com.angelozero.cl0ud.entrypoint.person.rest.request.PersonRequest;
import com.angelozero.cl0ud.entrypoint.person.rest.response.PersonResponse;
import com.angelozero.cl0ud.usecase.person.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonRestMapper {

    Person toModel(PersonRequest personRequest);

    List<PersonResponse> toResponseList(List<Person> personList);

    PersonResponse toResponse(Person person);
}
