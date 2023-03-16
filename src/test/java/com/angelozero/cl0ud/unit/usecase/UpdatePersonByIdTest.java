package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.person.UpdatePersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.GetPersonById;
import com.angelozero.cl0ud.usecase.UpdatePerson;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdatePersonByIdTest {


    @Mock
    private DataBaseGateway dataBaseGateway;

    @Mock
    private GetPersonById getPersonById;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private UpdatePerson updatePerson;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }

    @DisplayName("Should fail to update a person - sending a null person")
    @Test
    void testDeletePersonWithANullPersonException() {

        UpdatePersonException exception = assertThrows(UpdatePersonException.class, () -> updatePerson.execute(null));

        verify(getPersonById, times(0)).execute(anyLong());
        verify(dataBaseGateway, times(0)).updatePerson(any(PersonEntity.class));
        verify(personMapper, times(0)).toModel(any(PersonEntity.class));

        assertFalse(isNull(exception));
        assertEquals("[Update Person Service] - Person Data and/or Person ID is null", exception.getMessage());
    }

    @DisplayName("Should fail to update a person - sending a null person id")
    @Test
    void testDeletePersonWithANullPersonIdException() {

        UpdatePersonException exception = assertThrows(UpdatePersonException.class, () -> updatePerson.execute(null));

        verify(getPersonById, times(0)).execute(anyLong());
        verify(dataBaseGateway, times(0)).updatePerson(any(PersonEntity.class));
        verify(personMapper, times(0)).toModel(any(PersonEntity.class));

        assertFalse(isNull(exception));
        assertEquals("[Update Person Service] - Person Data and/or Person ID is null", exception.getMessage());
    }

    @DisplayName("Should fail to update a person")
    @Test
    void testDeletePersonWithException() {

        Person personFixture = Fixture
                .from(Person.class)
                .gimme("valid Person without id");

        UpdatePersonException exception = assertThrows(UpdatePersonException.class, () -> updatePerson.execute(personFixture));

        verify(getPersonById, times(0)).execute(anyLong());
        verify(dataBaseGateway, times(0)).updatePerson(any(PersonEntity.class));
        verify(personMapper, times(0)).toEntity(any(Person.class));
        verify(personMapper, times(0)).toModel(any(PersonEntity.class));

        assertFalse(isNull(exception));
        assertEquals("[Update Person Service] - Person Data and/or Person ID is null", exception.getMessage());
    }

    @DisplayName("Should update a person with success")
    @Test
    void testUpdatePersonWithSuccess() {

        PersonEntity personEntityFixture = Fixture
                .from(PersonEntity.class)
                .gimme(PersonEntityTemplate.VALID_PERSON_ENTITY);

        Person personFixture = Fixture
                .from(Person.class)
                .gimme(PersonTemplate.VALID_PERSON);

        when(personMapper.toEntity(any(Person.class))).thenReturn(personEntityFixture);
        when(personMapper.toModel(any(PersonEntity.class))).thenReturn(personFixture);
        when(getPersonById.execute(anyLong())).thenReturn(personFixture);
        when(dataBaseGateway.updatePerson(any((PersonEntity.class)))).thenReturn(personEntityFixture);

        assertDoesNotThrow(() -> updatePerson.execute(personFixture));

        verify(personMapper, times(1)).toEntity(any(Person.class));
        verify(personMapper, times(1)).toModel(any(PersonEntity.class));
        verify(getPersonById, times(1)).execute(anyLong());
        verify(dataBaseGateway, times(1)).updatePerson(any(PersonEntity.class));

    }
}
