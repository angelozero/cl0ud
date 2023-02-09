package com.angelozero.cl0ud.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerOpenAPIConfig {

    @Bean
    public OpenAPI setup() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cl0ud API")
                        .description("REST API simple application")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github - angelozero")
                        .url("https://github.com/angelozero/cl0ud"));
    }
}