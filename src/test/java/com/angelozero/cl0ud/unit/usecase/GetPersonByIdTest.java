package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.person.GetPersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.GetPersonById;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.ztemplate.person.PersonEntityTemplate;
import com.angelozero.cl0ud.ztemplate.person.PersonTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetPersonByIdTest {

    @Mock
    private DataBaseGateway dataBaseGateway;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private GetPersonById getPersonById;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate.person");
    }

    @DisplayName("Should fail to get a person by id")
    @Test
    void testGetPersonByIdWithException() {

        when(dataBaseGateway.findPersonEntityById(anyLong())).thenThrow(new RuntimeException("Test Error"));

        GetPersonException exception = assertThrows(GetPersonException.class, () -> getPersonById.execute(new Random().nextLong()));

        verify(dataBaseGateway, times(1)).findPersonEntityById(anyLong());
        verify(personMapper, times(0)).toModel(any(PersonEntity.class));

        assertFalse(isNull(exception));
        assertEquals("[Get Person Service] - Error to find a person by ID: Test Error", exception.getMessage());
    }

    @DisplayName("Should fail to get a person by with null id")
    @Test
    void testDeletePersonWithNullIdException() {

        GetPersonException exception = assertThrows(GetPersonException.class, () -> getPersonById.execute(null));

        verify(personMapper, times(0)).toModel(any(PersonEntity.class));
        verify(dataBaseGateway, times(0)).findPersonEntityById(anyLong());

        assertFalse(isNull(exception));
        assertEquals("[Get Person Service] - Person ID is null", exception.getMessage());
    }

    @DisplayName("Should get a person by id with success")
    @Test
    void testGetPersonByIdWithSuccess() {

        PersonEntity personEntityFixture = Fixture
                .from(PersonEntity.class)
                .gimme(PersonEntityTemplate.VALID_PERSON_ENTITY);

        Person personFixture = Fixture
                .from(Person.class)
                .gimme(PersonTemplate.VALID_PERSON);


        when(dataBaseGateway.findPersonEntityById(anyLong())).thenReturn(Optional.of(personEntityFixture));
        when(personMapper.toModel(any(PersonEntity.class))).thenReturn(personFixture);

        Person response = getPersonById.execute(new Random().nextLong());

        verify(dataBaseGateway, times(1)).findPersonEntityById(anyLong());
        verify(personMapper, times(1)).toModel(any(PersonEntity.class));

        assertFalse(isNull(response));
    }

}
