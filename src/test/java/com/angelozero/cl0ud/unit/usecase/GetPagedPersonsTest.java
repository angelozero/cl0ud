package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.person.GetAllPagedPersonsException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.GetPagedPersons;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetPagedPersonsTest {

    @Mock
    private DataBaseGateway dataBaseGateway;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private GetPagedPersons getPagedPersons;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }


    @DisplayName("Should fail to get all paged persons")
    @Test
    void testGetAllPagedPersonsWithException() {

        when(dataBaseGateway.getPagedPersonsEntity(any())).thenThrow(new RuntimeException("Test Error"));

        GetAllPagedPersonsException exception = assertThrows(GetAllPagedPersonsException.class, () -> getPagedPersons.execute(PageRequest.of(0, 1)));

        verify(dataBaseGateway, times(1)).getPagedPersonsEntity(any());
        verify(personMapper, times(0)).toModel(any());

        assertFalse(isNull(exception));
        assertEquals("[Get All Paged Persons Service] - Error to get all paged persons: Test Error", exception.getMessage());
    }

    @DisplayName("Should get all paged persons with success")
    @Test
    void testGetAllPagedPersonsWithSuccess() {

        List<PersonEntity> personEntityListFixture = Fixture
                .from(PersonEntity.class)
                .gimme(1, PersonEntityTemplate.VALID_PERSON_ENTITY);

        Person personFixture = Fixture
                .from(Person.class)
                .gimme(PersonTemplate.VALID_PERSON);

        Page<PersonEntity> pagedPersonEntity = new PageImpl<>(personEntityListFixture);


        when(dataBaseGateway.getPagedPersonsEntity(any())).thenReturn(pagedPersonEntity);
        lenient().when(personMapper.toModel(any())).thenReturn(personFixture);

        Page<Person> response = getPagedPersons.execute(PageRequest.of(0, 1));

        verify(dataBaseGateway, times(1)).getPagedPersonsEntity(any());
        verify(personMapper, times(1)).toModel(any());

        assertFalse(isNull(response));
        assertFalse(response.isEmpty());
    }
}
