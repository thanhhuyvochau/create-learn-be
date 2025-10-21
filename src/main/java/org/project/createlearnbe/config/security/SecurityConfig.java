package org.project.createlearnbe.config.security;

import org.project.createlearnbe.config.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  public static final HttpMethod ALL = null;

  /**
   * Each entry: (HttpMethod, URL) If method == ALL, it applies to all HTTP methods for that URL.
   */
  private static final List<PermitRule> PUBLIC_ENDPOINTS =
      List.of(
          new PermitRule(HttpMethod.POST, "/api/auth/login"),
          new PermitRule(HttpMethod.POST, "/api/auth/refresh"),
          new PermitRule(ALL, "/v3/api-docs/**"),
          new PermitRule(ALL, "/swagger-ui.html"),
          new PermitRule(ALL, "/swagger-ui/**"),
          new PermitRule(HttpMethod.GET, "/api/teachers/**"),
          new PermitRule(HttpMethod.GET, "/api/subjects/**"),
          new PermitRule(HttpMethod.GET, "/api/grades/**"),
          new PermitRule(HttpMethod.POST, "/api/consultations/**"),
          new PermitRule(HttpMethod.GET, "/api/news/public/**"),
          new PermitRule(HttpMethod.GET, "/api/classes/public/**"),
          new PermitRule(HttpMethod.POST, "/api/registrations"));

  private final JwtFilter jwtFilter;

  public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    HttpSecurity httpSecurity = http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults());

    httpSecurity
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth -> {
              auth.anyRequest().permitAll();
            })
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  // **Authentication Applied**
  //  @Bean
  //  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  //    HttpSecurity httpSecurity = http.csrf(AbstractHttpConfigurer::disable).cors(cors -> {});
  //
  //    httpSecurity.sessionManagement(sm ->
  // sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
  //            .authorizeHttpRequests(
  //                    auth -> {
  //                      // Apply each permit rule
  //                      PUBLIC_ENDPOINTS.forEach(
  //                              rule -> {
  //                                if (rule.method() == ALL) {
  //                                  auth.requestMatchers(rule.pattern()).permitAll();
  //                                } else {
  //                                  auth.requestMatchers(rule.method(),
  // rule.pattern()).permitAll();
  //                                }
  //                              });
  //
  //                      auth.anyRequest().authenticated();
  //                    })
  //            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  //
  //    return httpSecurity.build();
  //  }

  private record PermitRule(HttpMethod method, String pattern) {}

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // ⚠️ WARNING: CorsConfiguration.ALL uses the wildcard "*"
    // This achieves the goal of allowing "all traffic" but is NOT recommended for production.

    // Allow all origins (all domains) using patterns to support credentials
    // Fixes: java.lang.IllegalArgumentException when setAllowCredentials(true) is used with "*"
    configuration.setAllowedOriginPatterns(
        List.of("https://create-learn-ui.vercel.app/", "http://localhost:3000"));

    // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
    configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));

    // Allow all headers (e.g., Authorization, Content-Type)
    configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));

    // Allow credentials (important for session cookies or custom headers like Authorization)
    configuration.setAllowCredentials(true);

    // Define which headers the browser is allowed to expose to the client (optional)
    configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));

    // Cache the preflight response for 1 hour (3600 seconds)
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // Apply this configuration to all endpoints ("/**")
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
