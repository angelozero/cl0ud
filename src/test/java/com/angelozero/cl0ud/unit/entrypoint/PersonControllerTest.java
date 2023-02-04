package com.angelozero.cl0ud.unit.entrypoint;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.entrypoint.PersonController;
import com.angelozero.cl0ud.entrypoint.mapper.PersonRestMapper;
import com.angelozero.cl0ud.entrypoint.rest.request.PersonRequest;
import com.angelozero.cl0ud.entrypoint.rest.response.PersonResponse;
import com.angelozero.cl0ud.usecase.*;
import com.angelozero.cl0ud.usecase.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final String ID = "person_id";

    @Mock
    private PersonRestMapper personRestMapper;

    @Mock
    private CreatePerson createPersonUseCase;

    @Mock
    private UpdatePerson updatePersonUseCase;

    @Mock
    private GetAllPersons getAllPersonsUseCase;

    @Mock
    private GetPersonById getPersonByIdUseCase;

    @Mock
    private DeletePersonById deletePersonByIdUseCase;

    @InjectMocks
    private PersonController controller;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.template");
    }

    @DisplayName("Should exist a GET url /person")
    @Test
    void testGetAllPersons() {

        Person personFixture = Fixture.from(Person.class).gimme("valid Person");
        List<PersonResponse> personResponseListFixture = Fixture.from(PersonResponse.class)
                .gimme(1, "valid PersonResponse");

        when(personRestMapper.toResponseList(anyList())).thenReturn(personResponseListFixture);
        when(getAllPersonsUseCase.execute()).thenReturn(Collections.singletonList(personFixture));

        ResponseEntity<List<PersonResponse>> response = controller.getPersons();

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
        assertTrue(response.getBody().size() > 0);
    }

    @DisplayName("Should exist a GET url /person/{id}")
    @Test
    void testGetPersonById() {

        Person personFixture = Fixture.from(Person.class).gimme("valid Person");
        PersonResponse personResponseFixture = Fixture.from(PersonResponse.class).gimme("valid PersonResponse");

        when(personRestMapper.toResponse(any(Person.class))).thenReturn(personResponseFixture);
        when(getPersonByIdUseCase.execute(anyLong())).thenReturn(personFixture);

        ResponseEntity<PersonResponse> response = controller.getPersonById(new Random().nextLong());

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
    }

    @DisplayName("Should exist POST url /person")
    @Test
    void testPostPerson() {

        Person personFixture = Fixture.from(Person.class).gimme("valid Person");
        PersonRequest personRequestFixture = Fixture.from(PersonRequest.class).gimme("valid PersonRequest");
        PersonResponse personResponseFixture = Fixture.from(PersonResponse.class).gimme("valid PersonResponse");

        when(personRestMapper.toModel(any(PersonRequest.class))).thenReturn(personFixture);
        when(personRestMapper.toResponse(any(Person.class))).thenReturn(personResponseFixture);
        when(createPersonUseCase.execute(any(Person.class))).thenReturn(personFixture);

        ResponseEntity<Void> response = controller.createPerson(personRequestFixture);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getHeaders().get(ID)));
        assertTrue(Objects.requireNonNull(response.getHeaders().get(ID)).size() > 0);
    }

    @DisplayName("Should exist PUT url /person")
    @Test
    void testPutPerson() {

        Person personFixture = Fixture.from(Person.class).gimme("valid Person");
        PersonRequest personRequestFixture = Fixture.from(PersonRequest.class).gimme("valid PersonRequest");

        when(personRestMapper.toModel(any(PersonRequest.class))).thenReturn(personFixture);
        doNothing().when(updatePersonUseCase).execute(any(Person.class));

        ResponseEntity<Void> response = controller.updatePerson(personRequestFixture);

        assertFalse(Objects.isNull(response));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("Should exist DELETE url /person")
    @Test
    void testDeletePerson() {

        doNothing().when(deletePersonByIdUseCase).execute(anyLong());

        ResponseEntity<Void> response = controller.deletePersonById(new Random().nextLong());

        assertFalse(Objects.isNull(response));
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}
