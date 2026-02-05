package com.example.issuetracker.config;

import io.swagger.v3.oas.models.security.SecurityRequirement; // ✅ THIS ONE
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IssueTracker")
                        .description("API documentation for Issue Tracker application")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Chetana Mahant")
                                .email("chetanamahant@gmail.com")
                        )
                )

                // 🔐 JWT security
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}

