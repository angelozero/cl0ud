package com.angelozero.cl0ud.entrypoint.person;

import com.angelozero.cl0ud.entrypoint.person.mapper.PersonRestMapper;
import com.angelozero.cl0ud.entrypoint.person.rest.request.PersonRequest;
import com.angelozero.cl0ud.entrypoint.person.rest.response.PersonResponse;
import com.angelozero.cl0ud.usecase.person.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PersonController {

    private static final String ID = "person_id";

    private PersonRestMapper personRestMapper;

    private final CreatePerson createPersonUseCase;
    private final UpdatePerson updatePersonUseCase;
    private final GetAllPersons getAllPersonsUseCase;
    private final GetPersonById getPersonByIdUseCase;
    private final DeletePersonById deletePersonByIdUseCase;

    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonResponse>> getPersons() {
        return new ResponseEntity<>(personRestMapper.toResponseList(getAllPersonsUseCase.execute()), HttpStatus.OK);
    }

    @GetMapping(value = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personRestMapper.toResponse(getPersonByIdUseCase.execute(id)));
    }

    @PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(@RequestBody PersonRequest personRequest) {
        PersonResponse personResponse = personRestMapper
                .toResponse(createPersonUseCase.execute(personRestMapper.toModel(personRequest)));

        return ResponseEntity
                .noContent()
                .header(ID, String.valueOf(personResponse.getId()))
                .build();
    }

    @PutMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePerson(@RequestBody PersonRequest personRequest) {
        updatePersonUseCase.execute(personRestMapper.toModel(personRequest));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/person/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable("id") Long id) {
        deletePersonByIdUseCase.execute(id);
        return ResponseEntity.accepted().build();
    }
}
