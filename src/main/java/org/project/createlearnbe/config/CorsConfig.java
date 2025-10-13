package org.project.createlearnbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ✅ Global CORS configuration for Spring Boot 3.5.5 (MVC + Security)
 * Allows all origins, headers, and methods.
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // apply to all endpoints
                        .allowedOriginPatterns("*") // allow all origins (for Spring Boot 3+)
                        .allowedMethods("*") // allow all HTTP methods
                        .allowedHeaders("*") // allow all headers
                        .allowCredentials(true) // allow cookies, Authorization headers
                        .maxAge(3600); // cache preflight requests for 1 hour
            }
        };
    }
}
