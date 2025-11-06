package org.project.createlearnbe.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private Key getAccessKey() {
    // min 256-bit
    String ACCESS_SECRET = "SuperSecretAccessKeyForJwtGeneration123456";
    return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
  }

  private Key getRefreshKey() {
    // min 256-bit
    String REFRESH_SECRET = "SuperSecretRefreshKeyForJwtGeneration654321";
    return Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
  }

  public String generateAccessToken(Map<String, String> claims) {
    long ACCESS_EXPIRATION = 1000 * 60 * 15; // 15 minutes
    return Jwts.builder()
        .setSubject(claims.get("username"))
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
        .signWith(getAccessKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(String username) {
    // 7 days
    long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7;
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
        .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsernameFromAccessToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getAccessKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String extractUsernameFromRefreshToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getRefreshKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateAccessToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getAccessKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public boolean validateRefreshToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getRefreshKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
