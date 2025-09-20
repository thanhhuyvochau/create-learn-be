package org.project.createlearnbe.controllers;

import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.AuthRequest;
import org.project.createlearnbe.dto.request.RegisterRequest;
import org.project.createlearnbe.dto.response.AccountResponse;
import org.project.createlearnbe.entities.Account;
import org.project.createlearnbe.serivce.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> login(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                accountService.register(request)
        ));
    }
}
