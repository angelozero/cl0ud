package com.angelozero.cl0ud.entrypoint;

import com.angelozero.cl0ud.usecase.*;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PersonController {

    public static final String ID = "person_id";
    private final CreatePerson createPerson;
    private final UpdatePerson updatePerson;
    private final GetAllPersons getAllPersons;
    private final GetPersonById getPersonById;
    private final DeletePersonById deletePersonById;

    @GetMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getPersons() {
        return new ResponseEntity<>(getAllPersons.execute(), HttpStatus.OK);
    }

    @GetMapping(value = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPersonById(@PathVariable("id") int id) {
        return new ResponseEntity<>(getPersonById.execute((long) id), HttpStatus.OK);
    }

    @PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {

        return ResponseEntity
                .ok()
                .header(ID, String.valueOf(createPerson.execute(person).getId()))
                .body(null);
    }

    @PutMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
        updatePerson.execute(person);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/person/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable("id") int id) {
        deletePersonById.execute((long) id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
