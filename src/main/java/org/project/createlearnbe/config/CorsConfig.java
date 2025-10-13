package org.project.createlearnbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    /**
     * ✅ 1. Universal CORS configuration (for all endpoints)
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    /**
     * ✅ 2. Extra headers to fix "strict-origin-when-cross-origin"
     * Adds proper referrer policy and cross-origin headers for browser compatibility.
     */
    @Bean
    public Filter addSecurityHeadersFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {

                HttpServletResponse res = (HttpServletResponse) response;

                // Required CORS headers
                res.setHeader("Access-Control-Allow-Origin", "*");
                res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
                res.setHeader("Access-Control-Allow-Headers", "*");

                // ✅ Fix for "strict-origin-when-cross-origin"
                res.setHeader("Referrer-Policy", "no-referrer-when-downgrade");

                // Optional: extra compatibility headers for modern browsers
                res.setHeader("Cross-Origin-Resource-Policy", "cross-origin");
                res.setHeader("Cross-Origin-Opener-Policy", "same-origin-allow-popups");
                res.setHeader("Cross-Origin-Embedder-Policy", "unsafe-none");

                chain.doFilter(request, response);
            }
        };
    }
}
