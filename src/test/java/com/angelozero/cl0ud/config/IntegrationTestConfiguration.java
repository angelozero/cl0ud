package com.angelozero.cl0ud.config;


import org.junit.ClassRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(initializers = IntegrationTestConfiguration.Initializer.class)
public class IntegrationTestConfiguration {

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
}
