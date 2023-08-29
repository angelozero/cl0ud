package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.person.DeletePersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.usecase.DeletePersonById;
import com.angelozero.cl0ud.usecase.GetPersonById;
import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.ztemplate.person.PersonTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletePersonByIdTest {


    @Mock
    private DataBaseGateway dataBaseGateway;

    @Mock
    private GetPersonById getPersonById;

    @InjectMocks
    private DeletePersonById deletePersonById;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }

    @DisplayName("Should fail to delete a person")
    @Test
    void testShouldFailDeletePerson() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);

        when(getPersonById.execute(anyLong())).thenReturn(personFixture);
        doThrow(new RuntimeException("Test Error")).when(dataBaseGateway).deletePersonEntityById(anyLong());

        DeletePersonException exception = assertThrows(DeletePersonException.class, () -> deletePersonById.execute(new Random().nextLong()));

        verify(getPersonById, times(1)).execute(anyLong());
        verify(dataBaseGateway, times(1)).deletePersonEntityById(anyLong());

        assertFalse(isNull(exception));
        assertEquals("[Delete Person Service] - Error to delete a person: Test Error", exception.getMessage());
    }

    @DisplayName("Should fail to delete a person - null Id")
    @Test
    void testShouldFailDeletePersonWithNullId() {

        DeletePersonException exception = assertThrows(DeletePersonException.class, () -> deletePersonById.execute(null));

        verify(getPersonById, times(0)).execute(anyLong());
        verify(dataBaseGateway, times(0)).deletePersonEntityById(anyLong());

        assertFalse(isNull(exception));
        assertEquals("[Delete Person Service] - Person ID is null", exception.getMessage());
    }

    @DisplayName("Should fail to delete a person when calling get person by id use case")
    @Test
    void testDeletePersonWithAnErrorReturnedWhenCallingGetPersonByIdUseCase() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);
        personFixture.setId(10L);

        when(getPersonById.execute(anyLong())).thenReturn(null);

        DeletePersonException exception = assertThrows(DeletePersonException.class, () -> deletePersonById.execute(personFixture.getId()));

        verify(getPersonById, times(1)).execute(anyLong());
        verify(dataBaseGateway, times(0)).deletePersonEntityById(anyLong());

        assertFalse(isNull(exception));
        assertEquals("[Delete Person Service] - Fail to delete a person - No person was found to be deleted: person-id: 10", exception.getMessage());
    }

    @DisplayName("Should delete a person with success")
    @Test
    void testDeletePersonWithSuccess() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);

        when(getPersonById.execute(anyLong())).thenReturn(personFixture);
        doNothing().when(dataBaseGateway).deletePersonEntityById(anyLong());

        assertDoesNotThrow(() -> deletePersonById.execute(new Random().nextLong()));

        verify(getPersonById, times(1)).execute(anyLong());
        verify(dataBaseGateway, times(1)).deletePersonEntityById(anyLong());
    }
}
