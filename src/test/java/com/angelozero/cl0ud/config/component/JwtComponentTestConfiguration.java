package com.angelozero.cl0ud.config.component;


import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.RefreshTokenRepository;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileReader;
import java.io.IOException;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = JwtComponentTestConfiguration.Initializer.class)
public class JwtComponentTestConfiguration {

    protected static final String AUTH_API_URL = "/api/v1/auth";

    @Autowired
    protected UserRepository repository;

    @Autowired
    protected RefreshTokenRepository refreshTokenrepository;

    /**
     * Testcontainer - REDIS
     */
    @ClassRule
    public static GenericContainer redisContainer = new GenericContainer("redis:latest")
            .withExposedPorts(6379);


    /**
     * Testcontainer - PostgreSQL
     */
    @ClassRule
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("cl0ud-jwt-component-tests-db")
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
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.redis.port=" + redisContainer.getFirstMappedPort()
            ).applyTo(configurableApplicationContext.getEnvironment());

            //Setting Redis config values in runtime
            System.setProperty("redis.port.value", redisContainer.getFirstMappedPort().toString());
        }
    }

    /**
     * Get json files
     */
    protected String getJsonFileValue(String file) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(
                    new FileReader("src/test/resources/json/responses/" + file));

            return data.toJSONString();
        } catch (IOException | ParseException e) {
            throw new RuntimeException("[ERROR] - [AUTH COMPONENT TESTS]: " + e.getMessage());
        }
    }

    public void clearDataRepository() {
        this.refreshTokenrepository.deleteAll();
        this.repository.deleteAll();
    }
}
