package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CreatePerson {

    public void execute(Person person) {
        log.info("[CLOUD-APP] - Creating a person: {}", person);
    }
}
