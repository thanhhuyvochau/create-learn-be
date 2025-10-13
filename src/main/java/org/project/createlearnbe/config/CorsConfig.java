package org.project.createlearnbe.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Allow all origins (supports credentials)
        config.addAllowedOriginPattern("*");
        config.setAllowCredentials(true);

        // ✅ Allow all headers & methods
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    /**
     * ✅ Extra security & referrer policy headers to handle strict-origin-when-cross-origin
     */
    @Bean
    public Filter addSecurityHeadersFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {

                HttpServletResponse res = (HttpServletResponse) response;

                // Allow cross-origin requests
                res.setHeader("Access-Control-Allow-Origin", "*");
                res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS");
                res.setHeader("Access-Control-Allow-Headers", "*");

                // ✅ Fix the strict-origin-when-cross-origin behavior
                res.setHeader("Referrer-Policy", "no-referrer-when-downgrade");

                // Optional: helpful for modern cross-origin setups
                res.setHeader("Cross-Origin-Resource-Policy", "cross-origin");
                res.setHeader("Cross-Origin-Opener-Policy", "same-origin-allow-popups");
                res.setHeader("Cross-Origin-Embedder-Policy", "unsafe-none");

                chain.doFilter(request, response);
            }
        };
    }
}
