package com.yurupari.user_service.config;

import com.yurupari.common_data.config.OpenApiConfig;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserOpenApiConfig {

    private final OpenApiConfig openApiConfig;

    @Value("${documentation.title}")
    private String title;

    @Value("${documentation.description}")
    private String description;

    @Value("${documentation.version}")
    private String version;

    @Bean
    public OpenAPI userServiceApiDocs() {
        return openApiConfig.serviceApiDocs(
                title,
                description,
                version
        );
    }
}
