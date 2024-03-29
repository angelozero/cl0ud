package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.person.CreatePersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.CreatePerson;
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

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatePersonTest {

    @Mock
    private DataBaseGateway dataBaseGateway;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private CreatePerson createPerson;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate.person");
    }

    @DisplayName("Should fail to create a person - send null person")
    @Test
    void testCreatePersonWithNullPersonException() {

        CreatePersonException exception = assertThrows(CreatePersonException.class, () -> createPerson.execute(null));

        verify(personMapper, times(0)).toEntity(any(Person.class));
        verify(dataBaseGateway, times(0)).savePerson(any(PersonEntity.class));
        verify(personMapper, times(0)).toModel(any(PersonEntity.class));

        assertFalse(isNull(exception));
        assertEquals("[Create Person Service] - Person Data is null", exception.getMessage());
    }

    @DisplayName("Should fail to create a person")
    @Test
    void testCreatePersonWithException() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);
        PersonEntity personEntityFixture = Fixture.from(PersonEntity.class).gimme(PersonEntityTemplate.VALID_PERSON_ENTITY);

        when(personMapper.toEntity(any(Person.class))).thenReturn(personEntityFixture);
        when(dataBaseGateway.savePerson(any(PersonEntity.class))).thenThrow(new RuntimeException("Test Error"));

        CreatePersonException exception = assertThrows(CreatePersonException.class, () -> createPerson.execute(personFixture));

        verify(personMapper, times(1)).toEntity(any(Person.class));
        verify(dataBaseGateway, times(1)).savePerson(any(PersonEntity.class));
        verify(personMapper, times(0)).toModel(any(PersonEntity.class));

        assertFalse(isNull(exception));
        assertEquals("[Create Person Service] - Error to create a person: Test Error", exception.getMessage());
    }

    @DisplayName("Should create a person with success")
    @Test
    void testCreatePersonWithSuccess() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);
        PersonEntity personEntityFixture = Fixture.from(PersonEntity.class).gimme(PersonEntityTemplate.VALID_PERSON_ENTITY);

        when(personMapper.toEntity(any(Person.class))).thenReturn(personEntityFixture);
        when(personMapper.toModel(any(PersonEntity.class))).thenReturn(personFixture);
        when(dataBaseGateway.savePerson(any(PersonEntity.class))).thenReturn(personEntityFixture);

        Person response = createPerson.execute(personFixture);

        verify(personMapper, times(1)).toEntity(any(Person.class));
        verify(dataBaseGateway, times(1)).savePerson(any(PersonEntity.class));
        verify(personMapper, times(1)).toModel(any(PersonEntity.class));

        assertFalse(isNull(response));
    }
}
