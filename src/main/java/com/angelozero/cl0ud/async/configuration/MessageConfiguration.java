package com.angelozero.cl0ud.async.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MessageConfiguration {
    @Value("${whatsapp.token}")
    private String token;

    @Value("${whatsapp.url}")
    private String url;

    @Value("${whatsapp.instance_id}")
    private String instanceId;

    @Value("${whatsapp.path}")
    private String path;
}
