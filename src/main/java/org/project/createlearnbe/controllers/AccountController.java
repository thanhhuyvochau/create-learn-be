package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.ChangePasswordByAdminRequest;
import org.project.createlearnbe.dto.request.ChangePasswordByOwnerRequest;
import org.project.createlearnbe.dto.request.ActivateAccountRequest;
import org.project.createlearnbe.dto.request.RegisterRequest;
import org.project.createlearnbe.dto.response.AccountResponse;
import org.project.createlearnbe.serivce.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Account Management", description = "APIs for managing user accounts")
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Operation(summary = "Register a new account", description = "Admin or user can register a new account in the system.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Account successfully created",
                  content = @Content(schema = @Schema(implementation = AccountResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input data",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  })
  @PostMapping
  public ResponseEntity<ApiWrapper<AccountResponse>> createAccount(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(accountService.register(request)));
  }

  @Operation(summary = "Get current user profile", description = "Retrieve profile details of the currently authenticated user.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                  content = @Content(schema = @Schema(implementation = AccountResponse.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized access",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  })
  @GetMapping("/profile")
  public ResponseEntity<ApiWrapper<AccountResponse>> getProfile() {
    return ResponseEntity.ok(ApiWrapper.success(accountService.getProfile()));
  }

  @Operation(summary = "Change user password by admin", description = "Allows an admin to change the password of any user by ID.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Password successfully changed",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class))),
          @ApiResponse(responseCode = "404", description = "User not found",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  })
  @PutMapping("/{id}/change-password/admin")
  public ResponseEntity<ApiWrapper<String>> changePasswordByAdmin(
          @Parameter(description = "ID of the account to change password for", required = true)
          @PathVariable UUID id,
          @RequestBody ChangePasswordByAdminRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(accountService.changePasswordByAdmin(id, request)));
  }

  @Operation(summary = "Change password by user", description = "Allows a user to change their own password using their username.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Password changed successfully",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class))),
          @ApiResponse(responseCode = "400", description = "Invalid old password or input data",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  })
  @PutMapping("/{username}/change-password")
  public ResponseEntity<ApiWrapper<String>> changePasswordByOwner(
          @Parameter(description = "Username of the account", required = true)
          @PathVariable String username,
          @RequestBody ChangePasswordByOwnerRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(accountService.changePasswordByOwner(username, request)));
  }

  @Operation(summary = "Activate or deactivate account", description = "Admin can activate or deactivate an account by ID.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Account status updated successfully",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class))),
          @ApiResponse(responseCode = "404", description = "Account not found",
                  content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  })
  @PutMapping("/{id}/activate")
  public ResponseEntity<ApiWrapper<String>> changeStatusAccount(
          @Parameter(description = "ID of the account to update", required = true)
          @PathVariable UUID id,
          @RequestBody ActivateAccountRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(accountService.deactivateAccount(id, request)));
  }
}
