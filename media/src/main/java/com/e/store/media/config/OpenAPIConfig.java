package com.e.store.media.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private final String moduleName;
    private final String apiVersion;

    public OpenAPIConfig(@Value("${module-name:Module}") String moduleName,
        @Value("${api-version:v1}") String apiVersion) {
        this.moduleName = moduleName;
        this.apiVersion = apiVersion;
    }

    @Bean
    public OpenAPI customAPI() {
        final String schemaName = "Bearer ";
        final String appTitle = String.format("%s API", StringUtils.capitalize(this.moduleName));

        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(schemaName)).components(
                new Components().addSecuritySchemes(schemaName,
                    new SecurityScheme().name(schemaName).type(Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            .info(new Info().title(appTitle).version(apiVersion));
    }
}
