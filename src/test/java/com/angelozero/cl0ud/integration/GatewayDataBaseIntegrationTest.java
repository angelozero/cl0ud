package com.angelozero.cl0ud.integration;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.config.integration.IntegrationTestConfiguration;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.gateway.postgressql.impl.PersonGatewayPostgresSql;
import com.angelozero.cl0ud.ztemplate.person.PersonEntityTemplate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class GatewayDataBaseIntegrationTest extends IntegrationTestConfiguration {


    @Autowired
    private PersonGatewayPostgresSql gateway;

    @BeforeClass
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate.person");
    }

    @DisplayName("Should save a person with success")
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

    @DisplayName("Should update a person with success")
    @Test
    public void shouldUpdateAPerson() {

        PersonEntity personSaved = savePerson();

        String uuid = UUID.randomUUID().toString();
        String newPersonName = personSaved.getName() + "-" + uuid;
        personSaved.setName(newPersonName);

        PersonEntity result = gateway.updatePerson(personSaved);

        assertNotNull(result);
        assertEquals(personSaved.getId(), result.getId());
        assertEquals(newPersonName, result.getName());
        assertEquals(personSaved.getAge(), result.getAge());

        clearDataRepository();
    }

    @DisplayName("Should delete a person by id with success")
    @Test
    public void shouldDeleteAPersonById() {

        PersonEntity personSaved = savePerson();

        gateway.deletePersonEntityById(personSaved.getId());
        PersonEntity result = gateway.findPersonEntityById(personSaved.getId()).orElse(null);

        assertNull(result);

        clearDataRepository();
    }

    @DisplayName("Should find a person by id with success")
    @Test
    public void shouldFindAPersonById() {

        PersonEntity personSaved = savePerson();
        PersonEntity result = gateway.findPersonEntityById(personSaved.getId()).orElse(null);

        assertNotNull(result);
        assertEquals(personSaved.getId(), result.getId());
        assertEquals(personSaved.getName(), result.getName());
        assertEquals(personSaved.getAge(), result.getAge());

        clearDataRepository();
    }

    @DisplayName("Should find all persons with success")
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

    @DisplayName("Should find all persons paginated with success")
    @Test
    public void shouldFindAllPersonsPaginated() {

        PersonEntity personSaved = savePerson();
        Page<PersonEntity> result = gateway.getPagedPersonsEntity(PageRequest.of(0, 1));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertNotNull(result.getContent().get(0));
        assertNotNull(result.getContent().get(0).getId());
        assertEquals(personSaved.getName(), result.getContent().get(0).getName());
        assertEquals(personSaved.getAge(), result.getContent().get(0).getAge());

        clearDataRepository();
    }

    @DisplayName("Should find all persons paginated by name with success")
    @Test
    public void shouldFindAllPersonsPaginatedByName() {

        PersonEntity personSaved = savePerson();
        Page<PersonEntity> result = gateway.getPagedPersonsEntityByName(personSaved.getName(), PageRequest.of(0, 1));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertNotNull(result.getContent().get(0));
        assertNotNull(result.getContent().get(0).getId());
        assertEquals(personSaved.getName(), result.getContent().get(0).getName());
        assertEquals(personSaved.getAge(), result.getContent().get(0).getAge());

        clearDataRepository();
    }
}
