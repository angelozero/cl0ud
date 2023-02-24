package com.angelozero.cl0ud.usecase.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class PasswordEncode {

    public static final String PBKDF_2 = "pbkdf2";

    public String execute(String password) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(PBKDF_2, Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(PBKDF_2, encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());

        return passwordEncoder.encode(password);
    }
}
