package org.project.createlearnbe.controllers;

import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.AuthRequest;
import org.project.createlearnbe.serivce.JwtAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtAuthService authenticationService;

    public AuthController(JwtAuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                authenticationService.authenticate(request.getUsername(), request.getPassword())
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String, String>>> refresh(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(ApiResponse.success(
                authenticationService.refresh(request.get("refreshToken"))
        ));
    }
}
