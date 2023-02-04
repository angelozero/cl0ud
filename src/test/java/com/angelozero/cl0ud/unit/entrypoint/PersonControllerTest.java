package com.angelozero.cl0ud.unit.entrypoint;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.entrypoint.PersonController;
import com.angelozero.cl0ud.usecase.*;
import com.angelozero.cl0ud.usecase.model.Person;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private CreatePerson createPerson;

    @Mock
    private UpdatePerson updatePerson;

    @Mock
    private GetAllPersons getAllPersons;

    @Mock
    private GetPersonById getPersonById;

    @Mock
    private DeletePersonById deletePersonById;

    @InjectMocks
    private PersonController controller;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.template");
    }

    @DisplayName("Should exist url \\person")
    @Test
    void testGet() {

        Person personFixture = Fixture.from(Person.class).gimme("valid Person");

        when(getAllPersons.execute()).thenReturn(Collections.singletonList(personFixture));

        assertFalse(Objects.isNull(controller.getPersons()));
        assertFalse(Objects.isNull(controller.getPersons().getBody()));
        assertTrue(controller.getPersons().getBody().size() > 0);
        assertEquals(controller.getPersons().getClass(), ResponseEntity.class);
    }
}
