package com.upc.hechoenperu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200","http://18.118.30.237", "http://localhost")  // Puedes restringir esto a tus dominios específicos
                        .allowedMethods("DELETE", "GET", "POST", "PUT")
                        .allowedHeaders("x-requested-with", "authorization", "Content-Type", "Authorization", "credential", "X-XSRF-TOKEN")
                        .exposedHeaders("x-requested-with", "authorization", "Content-Type", "Authorization", "credential", "X-XSRF-TOKEN")
                        .allowCredentials(true)  // Cambia a true si necesitas permitir cookies
                        .maxAge(3600);
            }
        };
    }
}
