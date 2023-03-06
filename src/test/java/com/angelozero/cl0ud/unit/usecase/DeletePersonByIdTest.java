package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.person.DeletePersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.usecase.DeletePersonById;
import com.angelozero.cl0ud.usecase.GetPersonById;
import com.angelozero.cl0ud.usecase.model.Person;
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
    void testDeletePersonWithException() {

        Person personFixture = Fixture.from(Person.class).gimme("valid Person");

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
    void testDeletePersonWithNullIdException() {

        DeletePersonException exception = assertThrows(DeletePersonException.class, () -> deletePersonById.execute(null));

        verify(getPersonById, times(0)).execute(anyLong());
        verify(dataBaseGateway, times(0)).deletePersonEntityById(anyLong());

        assertFalse(isNull(exception));
        assertEquals("[Delete Person Service] - ID is null", exception.getMessage());
    }

    @DisplayName("Should delete a person with success")
    @Test
    void testDeletePersonWithSuccess() {

        Person personFixture = Fixture.from(Person.class).gimme("valid Person");

        when(getPersonById.execute(anyLong())).thenReturn(personFixture);
        doNothing().when(dataBaseGateway).deletePersonEntityById(anyLong());

        assertDoesNotThrow(() -> deletePersonById.execute(new Random().nextLong()));

        verify(getPersonById, times(1)).execute(anyLong());
        verify(dataBaseGateway, times(1)).deletePersonEntityById(anyLong());
    }
}
