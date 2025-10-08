package org.project.createlearnbe.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
  public static Authentication getCurrentAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
