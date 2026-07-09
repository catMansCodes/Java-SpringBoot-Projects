package com.catmanscodes.weatherapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI weatherOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Weather API")
                        .description("CRUD API for weather records, backed by MySQL with a Redis cache layer.")
                        .version("v1")
                        .contact(new Contact().name("catmanscodes"))
                        .license(new License().name("Apache 2.0")));
    }
}
