package com.microservice.pruebatecnica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase para configurar swagguer
 */
@Configuration public class OpenApiConfig {
    /**
     * Spring OpenApi bean
     * <p>
     * Configuration to expose swagger
     * Title, description
     *
     * @return the open api
     */
    @Bean public OpenAPI springShopOpenAPI () {
        return new OpenAPI().info(
                new Info().title("Prueba tecnica API").description("Servicios expuestos del microservicio prueba tecnica")
                        .version("v0.0.1").license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
