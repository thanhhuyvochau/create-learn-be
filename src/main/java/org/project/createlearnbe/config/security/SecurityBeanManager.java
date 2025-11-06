package org.project.createlearnbe.config.security;

import java.util.Collections;
import org.project.createlearnbe.entities.Account;
import org.project.createlearnbe.repositories.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeanManager {
  private final AccountRepository accountRepository;

  public SecurityBeanManager(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    new Account();
    return username ->
        accountRepository
            .findByUsername(username)
            .map(
                account ->
                    new User(
                        account.getUsername(),
                        account.getPassword(),
                        Collections.singleton(
                            new SimpleGrantedAuthority("ROLE_" + account.getRole().getCode()))))
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }
}
