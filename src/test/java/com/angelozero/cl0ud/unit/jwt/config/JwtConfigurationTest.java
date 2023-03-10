package com.angelozero.cl0ud.unit.jwt.config;

import com.angelozero.cl0ud.exception.jwt.JwtConfigurationException;
import com.angelozero.cl0ud.jwt.config.JwtConfiguration;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtConfigurationTest {


    @Mock
    private UserRepository repository;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @InjectMocks
    private JwtConfiguration jwtConfiguration;

    @DisplayName("Should create UserDetailsService Bean with success")
    @Test
    public void shouldCreateUserDetailsServiceBean() {

        lenient().when(repository.findUserByEmail(anyString()))
                .thenReturn(UserEntity.builder().fullname("Full Name Test").build());

        UserDetailsService response = jwtConfiguration.userDetailsService();

        assertNotNull(response);
    }

    @DisplayName("Should generate an exception JwtConfigurationException")
    @Test
    public void shouldGenerateExceptionJwtConfigurationException() {

        doThrow(new RuntimeException("Error Test")).when(repository).findUserByEmail(anyString());

        JwtConfigurationException exception = assertThrows(JwtConfigurationException.class,
                () -> jwtConfiguration.userDetailsService().loadUserByUsername(StringUtils.EMPTY));

        assertNotNull(exception);
        assertEquals("[Jwt Configuration] - Error to find the user by email and generate the UserDetailsService Error Test",
                exception.getMessage());
    }

    @DisplayName("Should create AuthenticationProvider Bean with success")
    @Test
    public void shouldCreateAuthenticationProviderBeanWithSuccess() {

        lenient().when(repository.findUserByEmail(anyString()))
                .thenReturn(UserEntity.builder().fullname("Full Name Test").build());

        AuthenticationProvider response = jwtConfiguration.authenticationProvider();

        assertNotNull(response);
    }

    @DisplayName("Should create PasswordEncoder Bean with success")
    @Test
    public void shouldCreatePasswordEncoderBeanWithSuccess() {

        PasswordEncoder response = jwtConfiguration.passwordEncoder();

        assertNotNull(response);
    }

    @DisplayName("Should create authenticationManager Bean with success")
    @Test
    public void shouldCreateAuthenticationManagerBeanWithSuccess() throws Exception {

        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(null);

        assertDoesNotThrow(() -> jwtConfiguration.authenticationManager(authenticationConfiguration));
    }
}
