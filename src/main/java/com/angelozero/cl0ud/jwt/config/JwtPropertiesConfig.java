package com.angelozero.cl0ud.jwt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtPropertiesConfig {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.milliseconds}")
    private Integer milliseconds;

    @Value("${security.jwt.token.hours}")
    private Integer hours;
}
