package org.project.createlearnbe.serivce;

import org.jetbrains.annotations.NotNull;
import org.project.createlearnbe.entities.Account;
import org.project.createlearnbe.repositories.AccountRepository;
import org.project.createlearnbe.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtAuthService {
  private final AuthenticationManager authManager;
  private final JwtUtil jwtUtil;
  private AccountRepository accountRepository;

  public JwtAuthService(
      AuthenticationManager authManager, JwtUtil jwtUtil, AccountRepository accountRepository) {
    this.authManager = authManager;
    this.jwtUtil = jwtUtil;
    this.accountRepository = accountRepository;
  }

  public Map<String, String> authenticate(String username, String password) {
    try {
      // authenticate against DB via AuthenticationManager + UserDetailsService
      Authentication authenticate =
          authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      // if successful → generate tokens
      Account loginAccount =
          accountRepository
              .findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found"));

      HashMap<String, String> claims = getClaims(loginAccount);
      String accessToken = jwtUtil.generateAccessToken(claims);
      String refreshToken = jwtUtil.generateRefreshToken(username);

      return Map.of(
          "accessToken", accessToken,
          "refreshToken", refreshToken);
    } catch (AuthenticationException e) {
      throw new RuntimeException("Invalid credentials", e);
    }
  }

  public Map<String, String> refresh(String refreshToken) {
    if (jwtUtil.validateRefreshToken(refreshToken)) {
      String username = jwtUtil.extractUsernameFromRefreshToken(refreshToken);
      Account loginAccount =
          accountRepository
              .findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found"));
      HashMap<String, String> claims = getClaims(loginAccount);
      String newAccessToken = jwtUtil.generateAccessToken(claims);

      return Map.of(
          "accessToken", newAccessToken,
          "refreshToken", refreshToken);
    } else {
      throw new RuntimeException("Invalid refresh token");
    }
  }

  private static @NotNull HashMap<String, String> getClaims(Account loginAccount) {
    HashMap<String, String> claims = new HashMap<>();
    claims.put("sub", loginAccount.getUsername());
    claims.put("role", loginAccount.getRole().getCode());
    claims.put("email", loginAccount.getEmail());
    claims.put("phone", loginAccount.getPhone());
    return claims;
  }
}
