package org.project.createlearnbe.config.audit;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

  @NotNull
  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.of("system");
    }

    Object principal = authentication.getPrincipal();
    if (principal
        instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
      return Optional.ofNullable(userDetails.getUsername());
    }

    return Optional.ofNullable(authentication.getName());
  }
}
