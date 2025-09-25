package org.project.createlearnbe.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

  private SecurityUtil() {}

  /** Get the current Authentication object */
  public static Optional<Authentication> getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
  }

  /** Get the current username (from principal) */
  public static Optional<String> getCurrentUsername() {
    return getAuthentication()
        .map(Authentication::getPrincipal)
        .map(
            principal -> {
              if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername();
              } else {
                return principal.toString();
              }
            });
  }

  /** Check if user has a specific role */
  public static boolean hasRole(String role) {
    return getAuthentication()
        .map(Authentication::getAuthorities)
        .map(
            authorities ->
                authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role)))
        .orElse(false);
  }
}
