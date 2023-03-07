package com.angelozero.cl0ud.unit.jwt.config;

import com.angelozero.cl0ud.jwt.config.JwtPropertiesConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = JwtPropertiesConfiguration.class,
        properties = {"security.jwt.token.hours=24",
                "security.jwt.token.milliseconds=1000",
                "security.jwt.token.secret-key=4D6251655468566D597133743677397A24432646294A404E635266556A586E5A"})
public class JwtPropertiesConfigurationTest {

    @Autowired
    private JwtPropertiesConfiguration jwtPropertiesConfiguration;

    @DisplayName("Should get sign key with success")
    @Test
    public void shouldGetSignKeyWithSuccess() {

        assertEquals(24, jwtPropertiesConfiguration.getHours());
        assertEquals(1000, jwtPropertiesConfiguration.getMilliseconds());
        assertEquals("4D6251655468566D597133743677397A24432646294A404E635266556A586E5A", jwtPropertiesConfiguration.getSecretKey());
        assertNotNull(jwtPropertiesConfiguration.getSignKey());

    }
}
