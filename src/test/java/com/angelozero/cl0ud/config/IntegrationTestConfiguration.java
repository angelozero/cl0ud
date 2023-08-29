package com.angelozero.cl0ud.config;


import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import com.angelozero.cl0ud.gateway.repository.PersonRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = IntegrationTestConfiguration.Initializer.class)
public class IntegrationTestConfiguration {


    @Autowired
    protected PersonRepository repository;

    /**
     * Testcontainer - PostgreSQL
     */
    @ClassRule
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("cl0ud-integration-tests-db")
            .withUsername("user")
            .withPassword("password");


    /**
     * Configuration initializer - .properties file
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.datasource.username=" + container.getUsername()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    protected void clearDataRepository() {
        this.repository.deleteAll();
    }

    protected PersonEntity savePerson() {
        clearDataRepository();
        PersonEntity personEntityFixture = PersonEntity.builder()
                .age(30)
                .name("Person Test Name")
                .build();
        return repository.save(personEntityFixture);
    }

    protected List<PersonEntity> findAllPersons() {
        return repository.findAll();
    }

}
