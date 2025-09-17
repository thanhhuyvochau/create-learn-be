package org.project.createlearnbe.serivce;

import org.project.createlearnbe.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtAuthService {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public JwtAuthService(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> authenticate(String username, String password) {
        try {
            // authenticate against DB via AuthenticationManager + UserDetailsService
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // if successful → generate tokens
            String accessToken = jwtUtil.generateAccessToken(username);
            String refreshToken = jwtUtil.generateRefreshToken(username);

            return Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials", e);
        }
    }

    public Map<String, String> refresh(String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String username = jwtUtil.extractUsernameFromRefreshToken(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(username);

            return Map.of(
                    "accessToken", newAccessToken,
                    "refreshToken", refreshToken
            );
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
