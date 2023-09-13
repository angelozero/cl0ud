package com.angelozero.cl0ud.integration;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.angelozero.cl0ud.auth_jwt.gateway.TokenGateway;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import com.angelozero.cl0ud.config.integration.JwtIntegrationTestConfiguration;
import com.angelozero.cl0ud.ztemplate.jwt.UserEntityTemplate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class JwtGatewayDataBaseIntegrationTest extends JwtIntegrationTestConfiguration {


    @Autowired
    private TokenGateway gateway;

    @BeforeClass
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("com.angelozero.cl0ud.ztemplate");
    }

    @DisplayName("Should find a user by email with success")
    @Test
    public void shouldFindUserByEmailWithSuccess() {

        clearDataRepository();

        UserEntity userEntityFixture = Fixture.from(UserEntity.class).gimme(UserEntityTemplate.VALID_USER_ENTITY);

        gateway.save(userEntityFixture);

        UserEntity result = gateway.findUserByEmail(userEntityFixture.getEmail());

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(userEntityFixture.getFullname(), result.getFullname());
        assertEquals(userEntityFixture.getEmail(), result.getEmail());

        clearDataRepository();
    }

    @DisplayName("Should find a user by token with success")
    @Test
    public void shouldFindUserByTokenWithSuccess() {

        clearDataRepository();

        UserEntity userEntityFixture = Fixture.from(UserEntity.class).gimme(UserEntityTemplate.VALID_USER_ENTITY);

        gateway.save(userEntityFixture);
        gateway.save(RefreshTokenEntity.builder().token("token-integration-test").user(userEntityFixture).build());

        RefreshTokenEntity result = gateway.findByToken("token-integration-test");

        assertNotNull(result);
        assertNotNull(result.getUser());

        clearDataRepository(result, result.getUser());
    }
}
