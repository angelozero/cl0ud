package com.angelozero.cl0ud.integration;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.configs.ApplicationConfigIntegrationTest;
import com.angelozero.cl0ud.entrypoint.PersonController;
import com.angelozero.cl0ud.entrypoint.rest.request.PersonRequest;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class PersonControllerIntegrationTest extends ApplicationConfigIntegrationTest {

    private static final String PERSON_URL = "/person";
    private static final String PERSON_ID_URL = "/person/{id}";
    private static final String ID = "id";

    @Autowired
    private PersonController controller;

    private MockMvc mockMvc;

    @BeforeClass
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }

    @Before
    public void setUp() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("Teste teste teste")
    @Test
    public void shouldDoARequestToSaveAPerson() throws Exception {

        clearDataRepository();
        List<PersonEntity> personsList = findAllPersons();
        assertTrue(personsList.isEmpty());

        PersonRequest personRequestFixture = Fixture.from(PersonRequest.class).gimme("valid PersonRequest");

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(PERSON_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(personRequestFixture)))
                .andExpect(status().isNoContent())
                .andReturn();

        List<PersonEntity> personsFinalList = findAllPersons();
        assertFalse(personsFinalList.isEmpty());

        assertEquals(String.valueOf(personsFinalList.get(0).getId()), result.getResponse().getHeader("person_id"));
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());

        clearDataRepository();
    }


    @Test
    public void shouldDoARequestToGetAllPersons() throws Exception {

        PersonEntity personSaved = savePerson();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(PERSON_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        assertEquals(getJsonString(List.of(personSaved)), result.getResponse().getContentAsString());

        clearDataRepository();
    }

    @Test
    public void shouldDoARequestToGetAPersonById() throws Exception {

        PersonEntity personSaved = savePerson();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(PERSON_ID_URL, String.valueOf(personSaved.getId()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        assertEquals(getJsonString(personSaved), result.getResponse().getContentAsString());

        clearDataRepository();
    }

    @Test
    public void shouldDoARequestToUpdateAPerson() throws Exception {

        PersonEntity personSaved = savePerson();
        List<PersonEntity> personsList = findAllPersons();
        assertFalse(personsList.isEmpty());
        assertEquals(personsList.get(0).getName(), personSaved.getName());

        String uuid = UUID.randomUUID().toString();
        String newName = personSaved.getName() + "-" + uuid;

        PersonRequest personRequestFixture = PersonRequest.builder()
                .id(personSaved.getId())
                .age(personSaved.getAge())
                .name(newName)
                .build();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(PERSON_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getJsonString(personRequestFixture)))
                .andExpect(status().isNoContent())
                .andReturn();

        List<PersonEntity> personsFinalList = findAllPersons();
        assertFalse(personsFinalList.isEmpty());
        assertEquals(personsFinalList.get(0).getName(), newName);

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());

        clearDataRepository();
    }

    @Test
    public void shouldDoARequestToDeleteAPersonById() throws Exception {

        PersonEntity personSaved = savePerson();
        List<PersonEntity> personsList = findAllPersons();
        assertFalse(personsList.isEmpty());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(PERSON_ID_URL, personSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andReturn();

        List<PersonEntity> personsFinalList = findAllPersons();
        assertTrue(personsFinalList.isEmpty());

        assertEquals(HttpStatus.ACCEPTED.value(), result.getResponse().getStatus());

        clearDataRepository();
    }

    private PersonEntity savePerson() {
        clearDataRepository();
        PersonEntity personEntityFixture = Fixture.from(PersonEntity.class).gimme("valid PersonEntity");
        return repository.save(personEntityFixture);
    }

    private List<PersonEntity> findAllPersons() {
        return repository.findAll();
    }

    private String getJsonString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("[COMPONENT TEST][PERSON CONTROLLER] - Error to convert an object to json string");
        }
    }
}
