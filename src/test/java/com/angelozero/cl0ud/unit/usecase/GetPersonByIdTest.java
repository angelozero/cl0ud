package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.exs.GetPersonException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.GetPersonById;
import com.angelozero.cl0ud.usecase.mapper.PersonMapper;
import com.angelozero.cl0ud.usecase.model.Person;
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
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }

    @DisplayName("Should fail to get a person by id")
    @Test
    void testGetAllPersonsWithException() {

        when(dataBaseGateway.findPersonEntityById(anyLong())).thenThrow(new RuntimeException("Test Error"));

        GetPersonException exception = assertThrows(GetPersonException.class, () -> getPersonById.execute(new Random().nextLong()));

        verify(dataBaseGateway, times(1)).findPersonEntityById(anyLong());
        verify(personMapper, times(0)).toModel(any(PersonEntity.class));

        assertFalse(isNull(exception));
        assertEquals("[Get Person Service] - Error to find a person by ID: Test Error", exception.getMessage());
    }

    @DisplayName("Should get a person by id with success")
    @Test
    void testGetPersonByIdWithSuccess() {

        PersonEntity personEntityFixture = Fixture
                .from(PersonEntity.class)
                .gimme("valid PersonEntity");

        Person personFixture = Fixture
                .from(Person.class)
                .gimme("valid Person");


        when(dataBaseGateway.findPersonEntityById(anyLong())).thenReturn(Optional.of(personEntityFixture));
        when(personMapper.toModel(any(PersonEntity.class))).thenReturn(personFixture);

        Person response = getPersonById.execute(new Random().nextLong());

        verify(dataBaseGateway, times(1)).findPersonEntityById(anyLong());
        verify(personMapper, times(1)).toModel(any(PersonEntity.class));

        assertFalse(isNull(response));
    }

}
