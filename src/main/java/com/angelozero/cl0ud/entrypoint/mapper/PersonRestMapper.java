package com.angelozero.cl0ud.entrypoint.mapper;

import com.angelozero.cl0ud.entrypoint.rest.request.AsyncPersonRequest;
import com.angelozero.cl0ud.entrypoint.rest.request.PersonRequest;
import com.angelozero.cl0ud.entrypoint.rest.response.PersonResponse;
import com.angelozero.cl0ud.usecase.model.AsyncPerson;
import com.angelozero.cl0ud.usecase.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonRestMapper {

    Person toModel(PersonRequest personRequest);

    AsyncPerson toAsyncModel(AsyncPersonRequest asyncPersonRequest);

    List<PersonResponse> toResponseList(List<Person> personList);

    PersonResponse toResponse(Person person);
}
