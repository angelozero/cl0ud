package com.angelozero.cl0ud.entrypoint;

import com.angelozero.cl0ud.entrypoint.mapper.PersonRestMapper;
import com.angelozero.cl0ud.entrypoint.rest.request.PersonRequest;
import com.angelozero.cl0ud.entrypoint.rest.response.PersonResponse;
import com.angelozero.cl0ud.usecase.*;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor
public class PersonController {

    private static final String ID = "person_id";

    private PersonRestMapper personRestMapper;

    private final CreatePerson createPersonUseCase;
    private final UpdatePerson updatePersonUseCase;
    private final GetAllPersons getAllPersonsUseCase;
    private final GetPersonById getPersonByIdUseCase;
    private final DeletePersonById deletePersonByIdUseCase;

    @Autowired
    private final PagedResourcesAssembler<PersonResponse> assembler;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonResponse>> getPersons() {
        return new ResponseEntity<>(personRestMapper.toResponseList(getAllPersonsUseCase.execute()), HttpStatus.OK);
    }

    @GetMapping(value = "/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<PersonResponse>>> getPagedPersons(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Page<Person> pagedPerson = getAllPersonsUseCase.execute(PageRequest.of(page, size));
        Page<PersonResponse> pagedPersonsResponse = pagedPerson.map(person -> personRestMapper.toResponse(person));

        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(PersonController.class)
                        .getPagedPersons(page, size))
                .withSelfRel();

        return ResponseEntity.ok(assembler.toModel(pagedPersonsResponse, link));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personRestMapper.toResponse(getPersonByIdUseCase.execute(id)));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(@RequestBody PersonRequest personRequest) {
        PersonResponse personResponse = personRestMapper
                .toResponse(createPersonUseCase.execute(personRestMapper.toModel(personRequest)));

        return ResponseEntity
                .noContent()
                .header(ID, String.valueOf(personResponse.getId()))
                .build();
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePerson(@RequestBody PersonRequest personRequest) {
        updatePersonUseCase.execute(personRestMapper.toModel(personRequest));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable("id") Long id) {
        deletePersonByIdUseCase.execute(id);
        return ResponseEntity.accepted().build();
    }
}
