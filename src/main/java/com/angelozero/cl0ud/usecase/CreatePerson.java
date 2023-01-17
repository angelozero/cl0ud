package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.gateway.Cl0udGateway;
import com.angelozero.cl0ud.usecase.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CreatePerson {

    private final Cl0udGateway dataBaseGateway;

    public void execute(Person person) {
        try {
            log.info("[CLOUD-APP][SERVICE] - Creating a person: {}", person);
            dataBaseGateway.savePerson(person);
        } catch (Exception ex) {
            System.out.println("\n[ERRO] - Erro ao salvar uma pessoa\n");
        }
    }
}
