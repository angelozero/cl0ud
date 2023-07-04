package com.angelozero.cl0ud.unit.usecase;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.exception.person.GetAllPagedPersonsByNameException;
import com.angelozero.cl0ud.gateway.DataBaseGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.usecase.GetPagedPersonsByName;
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
public class GetPagedPersonsByNameTest {

    @Mock
    private DataBaseGateway dataBaseGateway;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private GetPagedPersonsByName getPagedPersonsByName;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }


    @DisplayName("Should fail to get all paged persons by name")
    @Test
    void testGetAllPagedPersonsByNameWithException() {

        when(dataBaseGateway.getPagedPersonsEntityByName(any(), any())).thenThrow(new RuntimeException("Test Error"));

        GetAllPagedPersonsByNameException exception = assertThrows(GetAllPagedPersonsByNameException.class, ()
                -> getPagedPersonsByName.execute("Name Test", PageRequest.of(0, 1)));

        verify(dataBaseGateway, times(1)).getPagedPersonsEntityByName(any(), any());
        verify(personMapper, times(0)).toModel(any());

        assertFalse(isNull(exception));
        assertEquals("[Get All Paged Persons By Name Service] - Error to get all paged persons by name: Name Test - Test Error", exception.getMessage());
    }

    @DisplayName("Should get all paged persons by name with success")
    @Test
    void testGetAllPagedPersonsByNameWithSuccess() {

        List<PersonEntity> personEntityListFixture = Fixture
                .from(PersonEntity.class)
                .gimme(1, PersonEntityTemplate.VALID_PERSON_ENTITY);

        Person personFixture = Fixture
                .from(Person.class)
                .gimme(PersonTemplate.VALID_PERSON);

        Page<PersonEntity> pagedPersonEntity = new PageImpl<>(personEntityListFixture);


        when(dataBaseGateway.getPagedPersonsEntityByName(any(), any())).thenReturn(pagedPersonEntity);
        lenient().when(personMapper.toModel(any())).thenReturn(personFixture);

        Page<Person> response = getPagedPersonsByName.execute("Name Test", PageRequest.of(0, 1));

        verify(dataBaseGateway, times(1)).getPagedPersonsEntityByName(any(), any());
        verify(personMapper, times(1)).toModel(any());

        assertFalse(isNull(response));
        assertFalse(response.isEmpty());
    }
}
