package com.angelozero.cl0ud.config;


import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.RefreshTokenRepository;
import com.angelozero.cl0ud.auth_jwt.gateway.repository.UserRepository;
import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
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

import java.util.List;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = JwtIntegrationTestConfiguration.Initializer.class)
public class JwtIntegrationTestConfiguration {


    @Autowired
    protected UserRepository repository;

    @Autowired
    protected RefreshTokenRepository refreshTokenrepository;

    /**
     * Testcontainer - PostgreSQL
     */
    @ClassRule
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("cl0ud-jwt-integration-tests-db")
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

    protected void clearDataRepository(RefreshTokenEntity refreshTokenEntity, UserEntity userEntity) {
        this.refreshTokenrepository.delete(refreshTokenEntity);
        this.repository.delete(userEntity);
    }

    protected UserEntity saveUser() {
        clearDataRepository();
        UserEntity userEntityFixture = UserEntity.builder()
                .email("email@test.com")
                .fullname("Angelo")
                .password("pass-word")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enable(true)
                .role("ADMIN")
                .build();
        return repository.save(userEntityFixture);
    }
}
