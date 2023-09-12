package com.angelozero.cl0ud.unit.entrypoint;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.entrypoint.PersonController;
import com.angelozero.cl0ud.entrypoint.mapper.PersonRestMapper;
import com.angelozero.cl0ud.entrypoint.rest.request.PersonRequest;
import com.angelozero.cl0ud.entrypoint.rest.response.PersonResponse;
import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.usecase.*;
import com.angelozero.cl0ud.ztemplate.person.PersonRequestTemplate;
import com.angelozero.cl0ud.ztemplate.person.PersonResponseTemplate;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Mock
    private GetPagedPersons getPagedPersonsUseCase;

    @Mock
    private GetPagedPersonsByName getPagedPersonsByNameUseCase;

    @Mock
    private PagedResourcesAssembler<PersonResponse> assembler;

    @InjectMocks
    private PersonController controller;

    @BeforeAll
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate.person");
    }

    @DisplayName("Should get all persons")
    @Test
    void testGetAllPersons() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);
        List<PersonResponse> personResponseListFixture = Fixture.from(PersonResponse.class)
                .gimme(1, PersonResponseTemplate.VALID_PERSON_RESPONSE);

        when(personRestMapper.toResponseList(anyList())).thenReturn(personResponseListFixture);
        when(getAllPersonsUseCase.execute()).thenReturn(Collections.singletonList(personFixture));

        ResponseEntity<List<PersonResponse>> response = controller.getPersons();

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
        assertTrue(response.getBody().size() > 0);
    }

    @DisplayName("Should get paged persons")
    @Test
    void testGetPagedPersons() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);

        PagedModel<EntityModel<PersonResponse>> pagedModelMock = PagedModel.of(
                Stream.of(PersonResponse.builder().build())
                        .map(EntityModel::of)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(10, 0, 20));

        when(getPagedPersonsUseCase.execute(any())).thenReturn(new PageImpl<>(Collections.singletonList(personFixture)));
        when(assembler.toModel(any(), any(Link.class))).thenReturn(pagedModelMock);

        ResponseEntity<PagedModel<EntityModel<PersonResponse>>> response = controller.getPagedPersons(0, 1);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
    }

    @DisplayName("Should get paged persons by name")
    @Test
    void testGetPagedPersonsByName() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);

        PagedModel<EntityModel<PersonResponse>> pagedModelMock = PagedModel.of(
                Stream.of(PersonResponse.builder().build())
                        .map(EntityModel::of)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(10, 0, 20));

        when(getPagedPersonsByNameUseCase.execute(any(), any())).thenReturn(new PageImpl<>(Collections.singletonList(personFixture)));
        when(assembler.toModel(any(), any(Link.class))).thenReturn(pagedModelMock);

        ResponseEntity<PagedModel<EntityModel<PersonResponse>>> response = controller.getPagedPersonsByName("Name Test", 0, 1);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
    }

    @DisplayName("Should get a person by id")
    @Test
    void testGetPersonById() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);
        PersonResponse personResponseFixture = Fixture.from(PersonResponse.class).gimme(PersonResponseTemplate.VALID_PERSON_RESPONSE);

        when(personRestMapper.toResponse(any(Person.class))).thenReturn(personResponseFixture);
        when(getPersonByIdUseCase.execute(anyLong())).thenReturn(personFixture);

        ResponseEntity<PersonResponse> response = controller.getPersonById(new Random().nextLong());

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getBody()));
    }

    @DisplayName("Should create a person")
    @Test
    void testPostPerson() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);
        PersonRequest personRequestFixture = Fixture.from(PersonRequest.class).gimme(PersonRequestTemplate.VALID_PERSON_REQUEST);
        PersonResponse personResponseFixture = Fixture.from(PersonResponse.class).gimme(PersonResponseTemplate.VALID_PERSON_RESPONSE);

        when(personRestMapper.toModel(any(PersonRequest.class))).thenReturn(personFixture);
        when(personRestMapper.toResponse(any(Person.class))).thenReturn(personResponseFixture);
        when(createPersonUseCase.execute(any(Person.class))).thenReturn(personFixture);

        ResponseEntity<Void> response = controller.createPerson(personRequestFixture);

        assertFalse(Objects.isNull(response));
        assertFalse(Objects.isNull(response.getHeaders().get(ID)));
        assertTrue(Objects.requireNonNull(response.getHeaders().get(ID)).size() > 0);
    }

    @DisplayName("Should update a person")
    @Test
    void testPutPerson() {

        Person personFixture = Fixture.from(Person.class).gimme(PersonTemplate.VALID_PERSON);
        PersonRequest personRequestFixture = Fixture.from(PersonRequest.class).gimme(PersonRequestTemplate.VALID_PERSON_REQUEST);

        when(personRestMapper.toModel(any(PersonRequest.class))).thenReturn(personFixture);
        doNothing().when(updatePersonUseCase).execute(any(Person.class));

        ResponseEntity<Void> response = controller.updatePerson(personRequestFixture);

        assertFalse(Objects.isNull(response));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("Should delete a person")
    @Test
    void testDeletePerson() {

        doNothing().when(deletePersonByIdUseCase).execute(anyLong());

        ResponseEntity<Void> response = controller.deletePersonById(new Random().nextLong());

        assertFalse(Objects.isNull(response));
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}
