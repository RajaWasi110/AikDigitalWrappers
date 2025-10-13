package com.aik.aikdigitalwrappers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blinkAccountOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AIK DIGITAL WRAPPERS")
                        .description("Wrappers for AIK Digital")
                        .version("1.0.0"));
    }
}
