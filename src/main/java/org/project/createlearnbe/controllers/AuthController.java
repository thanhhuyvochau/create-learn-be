package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.AuthRequest;
import org.project.createlearnbe.serivce.JwtAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Authentication", description = "Endpoints for user login and JWT token management")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtAuthService authenticationService;

    public AuthController(JwtAuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "User login",
            description = "Authenticate a user with username and password, returning access and refresh tokens."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = ApiWrapper.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<ApiWrapper<Map<String, String>>> login(
            @RequestBody(description = "Username and password for authentication",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AuthRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(ApiWrapper.success(
                authenticationService.authenticate(request.getUsername(), request.getPassword())
        ));
    }

    @Operation(
            summary = "Refresh JWT token",
            description = "Use a valid refresh token to obtain a new access token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                    content = @Content(schema = @Schema(implementation = ApiWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Invalid or expired refresh token",
                    content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<ApiWrapper<Map<String, String>>> refresh(
            @RequestBody(description = "Refresh token in JSON format (key: refreshToken)", required = true)
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> request
    ) {
        return ResponseEntity.ok(ApiWrapper.success(
                authenticationService.refresh(request.get("refreshToken"))
        ));
    }
}
