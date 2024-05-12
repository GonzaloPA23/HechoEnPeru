package com.upc.hechoenperu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hecho En Perú API")
                        .description("API de un ecommerce de productos artesanales hechos en Perú")
                        .version("1.0.0")
                        .license(new License()
                                .name("Licencia Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                        .contact(new Contact()
                                .name("Gonzalo PA")
                                .email("goldengpa23@gmail.com")
                                .url("https://www.api.com/contact")
                        )
                        .termsOfService("https://www.api.com/terms")
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("JwtScheme")
                )
                .components(new Components()
                        //JWT
                        .addSecuritySchemes("JwtScheme",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .description("Autorizar por un token JWT")
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

}
