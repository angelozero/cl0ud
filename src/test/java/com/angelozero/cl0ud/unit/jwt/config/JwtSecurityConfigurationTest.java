package com.angelozero.cl0ud.unit.jwt.config;

import com.angelozero.cl0ud.jwt.config.JwtSecurityConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class JwtSecurityConfigurationTest {


    @DisplayName("Should exist SecurityFilterChain Bean with securityFilterChain method")
    @Test
    public void shouldExistSecurityFilterChainBean() throws Exception {
        Class<JwtSecurityConfiguration> clazz = JwtSecurityConfiguration.class;

        assertNotNull(clazz.getDeclaredMethods());
        assertEquals(1, clazz.getDeclaredMethods().length);
        assertEquals("securityFilterChain", clazz.getDeclaredMethods()[0].getName());
    }
}
