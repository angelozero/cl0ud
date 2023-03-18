package com.angelozero.cl0ud;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Cl0ud - API"))
@SpringBootApplication
public class Cl0udApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cl0udApplication.class, args);
    }

}
