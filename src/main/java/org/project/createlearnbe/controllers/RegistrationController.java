package org.project.createlearnbe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.ChangeStatusRegistrationRequest;
import org.project.createlearnbe.dto.request.RegistrationRequest;
import org.project.createlearnbe.dto.response.RegistrationResponse;
import org.project.createlearnbe.serivce.RegistrationService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

  private final RegistrationService registrationService;

  @PostMapping
  public ResponseEntity<ApiResponse<RegistrationResponse>> create(
      @RequestBody RegistrationRequest request) {
    return ResponseEntity.ok(ApiResponse.success(registrationService.create(request)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<RegistrationResponse>> update(
      @PathVariable Long id, @RequestBody @Valid ChangeStatusRegistrationRequest request) {
    return ResponseEntity.ok(ApiResponse.success(registrationService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
    registrationService.delete(id);
    return ResponseEntity.ok(ApiResponse.success("Deleted successfully"));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<RegistrationResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(registrationService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<ApiPage<RegistrationResponse>>> getAll(Pageable pageable) {
    return ResponseEntity.ok(ApiResponse.success(ApiPage.of(registrationService.getAll(pageable))));
  }
}
