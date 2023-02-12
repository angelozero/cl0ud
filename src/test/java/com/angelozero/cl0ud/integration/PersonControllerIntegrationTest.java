package com.angelozero.cl0ud.integration;

import com.angelozero.cl0ud.configs.ApplicationConfigIntegrationTest;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
public class PersonControllerIntegrationTest extends ApplicationConfigIntegrationTest {


    @Test
    public void shuoldBeFirstTest() {
        repository.save(PersonEntity.builder()
                .name("Angelo")
                .age(20)
                .build());

        List<PersonEntity> lista = repository.findAll();

        assertNotNull(lista);
    }

}
