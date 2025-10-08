package org.project.createlearnbe.controllers;

import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.ChangePasswordByAdminRequest;
import org.project.createlearnbe.dto.request.ChangePasswordByOwnerRequest;
import org.project.createlearnbe.dto.request.DeactivateAccountRequest;
import org.project.createlearnbe.dto.request.RegisterRequest;
import org.project.createlearnbe.dto.response.AccountResponse;
import org.project.createlearnbe.serivce.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
      @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(ApiResponse.success(accountService.register(request)));
  }

  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<AccountResponse>> getProfile() {
    return ResponseEntity.ok(ApiResponse.success(accountService.getProfile()));
  }

  @PutMapping("/{id}/change-password/admin")
  public ResponseEntity<ApiResponse<String>> changePasswordByAdmin(
      @PathVariable UUID id, @RequestBody ChangePasswordByAdminRequest request) {
    return ResponseEntity.ok(
        ApiResponse.success(accountService.changePasswordByAdmin(id, request)));
  }

  @PutMapping("/{id}/change-password")
  public ResponseEntity<ApiResponse<String>> changePasswordByOwner(
      @PathVariable UUID id, @RequestBody ChangePasswordByOwnerRequest request) {
    return ResponseEntity.ok(
        ApiResponse.success(accountService.changePasswordByOwner(id, request)));
  }

  @PutMapping("/{id}/activate")
  public ResponseEntity<ApiResponse<String>> deactivateAccount(
      @PathVariable UUID id, @RequestBody DeactivateAccountRequest request) {
    return ResponseEntity.ok(ApiResponse.success(accountService.deactivateAccount(id, request)));
  }
}
