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
import org.springframework.web.cors.CorsConfigurationSource;

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

  @Autowired(required = false)
  @Qualifier("customCorsConfigurationSource")
  private CorsConfigurationSource corsConfigurationSource;

  public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    HttpSecurity httpSecurity = http.csrf(AbstractHttpConfigurer::disable);

    httpSecurity.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth -> {
//              // Apply each permit rule
//              PUBLIC_ENDPOINTS.forEach(
//                  rule -> {
//                    if (rule.method() == ALL) {
//                      auth.requestMatchers(rule.pattern()).permitAll();
//                    } else {
//                      auth.requestMatchers(rule.method(), rule.pattern()).permitAll();
//                    }
//                  });
//
//              auth.anyRequest().authenticated();
                auth.anyRequest().permitAll();
            })
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  private record PermitRule(HttpMethod method, String pattern) {}
}
