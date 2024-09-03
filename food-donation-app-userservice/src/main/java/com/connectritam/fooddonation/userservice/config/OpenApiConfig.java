package com.connectritam.fooddonation.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Donation App API")
                        .description("API documentation for the Food Donation App User Service")
                        .version("1.0.0"));
    }

    @Bean
    GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users-v1")
                .pathsToMatch("/api/v1/users/**")
                .build();
    }

    @Bean
    GroupedOpenApi userV2Api() {
        return GroupedOpenApi.builder()
                .group("users-v2")
                .pathsToMatch("/api/v2/users/**")
                .build();
    }

    @Bean
    GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth0.1")
                .pathsToMatch("/api/v0.1/auth/**")
                .build();
    }

    @Bean
    GroupedOpenApi auth02Api() {
        return GroupedOpenApi.builder()
                .group("auth0.2")
                .pathsToMatch("/api/v0.2/auth/**")
                .build();
    }
}