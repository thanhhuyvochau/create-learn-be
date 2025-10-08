package org.project.createlearnbe.controllers;

import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.RegisterRequest;
import org.project.createlearnbe.dto.response.AccountResponse;
import org.project.createlearnbe.serivce.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
