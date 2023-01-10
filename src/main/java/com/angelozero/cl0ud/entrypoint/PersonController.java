package com.angelozero.cl0ud.entrypoint;

import com.angelozero.cl0ud.usecase.GetPersonById;
import com.angelozero.cl0ud.usecase.GetPersons;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final GetPersons getPersons;
    private final GetPersonById getPersonById;

    @GetMapping("/person")
    public List<Person> getPersons() {
        return getPersons.execute();
    }

    @GetMapping("/person/{id}")
    public Person getPersonById(@PathVariable("id") int id) {
        return getPersonById.execute(id);
    }
}
