package com.angelozero.cl0ud.config;

import com.angelozero.cl0ud.usecase.jwt.*;
import com.angelozero.cl0ud.usecase.jwt.model.Permission;
import com.angelozero.cl0ud.usecase.jwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    UserDetailsService users() {
        return generateUserDetailServiceData();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());

        return passwordEncoder;
    }

    @Bean
    public DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            try {
                authorize
                        .requestMatchers(
                                "/auth/signin",
                                "/auth/refresh",
                                "/api-docs/**/",
                                "swagger-ui.html**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/users").denyAll()
                        .and()
                        .cors()
                        .and()
                        .apply(new JwtSecurityConfig());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        http.addFilterAfter(
                new TokenFilter(
                        new ResolveToken(),
                        new ValidateToken(
                                new DecodedToken(
                                        new JwtPropertiesConfig()
                                )
                        ),
                        new GetAuthentication(
                                new DecodedToken(
                                        new JwtPropertiesConfig()
                                ),
                                new CreateAccessToken(
                                        new JwtPropertiesConfig()
                                ),
                                generateUserDetailServiceData()
                        )
                ), BasicAuthenticationFilter.class);

        return http.build();
    }

    private InMemoryUserDetailsManager generateUserDetailServiceData() {
        UserDetails user = User.builder()
                .userName("username")
                .password("password")
                .permissions(Collections.singletonList(Permission.builder()
                        .description("admin")
                        .build()))
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
