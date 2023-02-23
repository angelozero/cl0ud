package com.angelozero.cl0ud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class JwtPropertiesConfig {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.milliseconds}")
    private Integer milliseconds;

    @Value("${security.jwt.token.hours}")
    private Integer hours;
}
