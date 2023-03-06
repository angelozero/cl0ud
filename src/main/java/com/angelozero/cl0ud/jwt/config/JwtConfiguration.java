package com.angelozero.cl0ud.jwt.config;

import com.angelozero.cl0ud.exception.jwt.JwtConfigurationException;
import com.angelozero.cl0ud.jwt.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtConfiguration {

    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        log.info("\n[JWT_CREATING_USER_DETAILS_SERVICE] - Creating UserDetailsService\n");
        return email -> {
            try {
                return repository.findUserByEmail(email);

            } catch (Exception ex) {
                log.error("\n[ERROR] - Error to create Jwt UserDetailsService\n");
                throw new JwtConfigurationException("Error to find the user by email and generate the UserDetailsService " + ex.getMessage());
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
