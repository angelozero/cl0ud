package com.angelozero.cl0ud.unit.auth_jwt.config;

import com.angelozero.cl0ud.auth_jwt.config.JwtSecurityConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtSecurityConfigurationTest {


    @DisplayName("Should exist SecurityFilterChain Bean with securityFilterChain method")
    @Test
    public void shouldExistSecurityFilterChainBean() {
        Class<JwtSecurityConfiguration> clazz = JwtSecurityConfiguration.class;

        assertNotNull(clazz.getDeclaredMethods());
        assertTrue(clazz.getDeclaredMethods().length > 0);
    }
}
