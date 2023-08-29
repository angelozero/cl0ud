package com.angelozero.cl0ud.integration;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.config.IntegrationTestConfiguration;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.gateway.postgressql.impl.PersonGatewayPostgresSql;
import com.angelozero.cl0ud.ztemplate.person.PersonEntityTemplate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class GatewayPostgresSqlIntegrationTest extends IntegrationTestConfiguration {


    @Autowired
    private PersonGatewayPostgresSql gateway;

    @BeforeClass
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }


    @Test
    public void shouldSaveAPerson() {

        clearDataRepository();

        PersonEntity personEntityFixture = Fixture.from(PersonEntity.class).gimme(PersonEntityTemplate.VALID_PERSON_ENTITY);

        PersonEntity result = gateway.savePerson(personEntityFixture);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(personEntityFixture.getName(), result.getName());
        assertEquals(personEntityFixture.getAge(), result.getAge());

        clearDataRepository();
    }


    @Test
    public void shouldFindAllPersons() {

        PersonEntity personSaved = savePerson();
        List<PersonEntity> result = gateway.getAllPersonsEntity();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(0).getId());
        assertEquals(personSaved.getName(), result.get(0).getName());
        assertEquals(personSaved.getAge(), result.get(0).getAge());

        clearDataRepository();
    }


    @Test
    public void shouldFindAPersonById() {

        PersonEntity personSaved = savePerson();

        Optional<PersonEntity> result = gateway.findPersonEntityById(personSaved.getId());

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertNotNull(result.get());
        assertEquals(personSaved.getId(), result.get().getId());
        assertEquals(personSaved.getName(), result.get().getName());
        assertEquals(personSaved.getAge(), result.get().getAge());

        clearDataRepository();
    }


    @Test
    public void shouldUpdateAPerson() {

        PersonEntity personSaved = savePerson();

        String uuid = UUID.randomUUID().toString();
        String newName = personSaved.getName() + "-" + uuid;
        personSaved.setName(newName);

        PersonEntity result = gateway.updatePerson(personSaved);

        assertNotNull(result);
        assertEquals(personSaved.getId(), result.getId());
        assertEquals(newName, result.getName());
        assertEquals(personSaved.getAge(), result.getAge());

        clearDataRepository();
    }


    @Test
    public void shouldDeleteAPerson() {

        PersonEntity personSaved = savePerson();

        assertFalse(findAllPersons().isEmpty());
        assertDoesNotThrow(() -> repository.deleteById(personSaved.getId()));
        assertTrue(findAllPersons().isEmpty());

        clearDataRepository();
    }
}
