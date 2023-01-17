package com.angelozero.cl0ud.entrypoint;

import com.angelozero.cl0ud.usecase.CreatePerson;
import com.angelozero.cl0ud.usecase.DeletePersonById;
import com.angelozero.cl0ud.usecase.GetAllPersons;
import com.angelozero.cl0ud.usecase.GetPersonById;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PersonController {

    private final CreatePerson createPerson;
//    private final GetAllPersons getAllPersons;
//    private final GetPersonById getPersonById;
//    private final DeletePersonById deletePersonById;

//    @GetMapping("/person")
//    public List<Person> getPersons() {
//        return getAllPersons.execute();
//    }
//
//    @GetMapping("/person/{id}")
//    public Person getPersonById(@PathVariable("id") int id) {
//        return getPersonById.execute(id);
//    }

    @PostMapping("/person")
    public String createPerson(@RequestBody Person person) {
        createPerson.execute(person);
        return "Person created with success";
    }

//    @DeleteMapping("/person/{id}")
//    public String deletePersonById(@PathVariable("id") int id) {
//        deletePersonById.execute(id);
//        return "Person deleted with success";
//    }
}
