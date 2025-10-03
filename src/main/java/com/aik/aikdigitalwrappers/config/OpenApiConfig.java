package com.aik.aikdigitalwrappers.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("AikDigitalWrappers API")
                .description("Wrapper layer for internal/external banking APIs")
                .version("1.0.0")
                .contact(new Contact().name("Aik Dev Team")));
    }
}