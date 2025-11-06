package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.ChangeStatusRegistrationRequest;
import org.project.createlearnbe.dto.request.RegistrationRequest;
import org.project.createlearnbe.dto.response.RegistrationResponse;
import org.project.createlearnbe.serivce.RegistrationService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Registration Management",
    description = "APIs for creating, updating, deleting, and retrieving class registrations")
@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

  private final RegistrationService registrationService;

  @Operation(
      summary = "Create a registration",
      description = "Creates a new registration record for a student or class participant.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Registration created successfully",
        content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
    @ApiResponse(responseCode = "400", description = "Invalid registration data")
  })
  @PostMapping
  public ResponseEntity<ApiWrapper<RegistrationResponse>> create(
      @RequestBody RegistrationRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(registrationService.create(request)));
  }

  @Operation(
      summary = "Update registration status",
      description = "Updates the status of an existing registration (e.g., approved, rejected).")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Registration status updated successfully",
        content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
    @ApiResponse(responseCode = "404", description = "Registration not found")
  })
  @PutMapping("/{id}")
  public ResponseEntity<ApiWrapper<RegistrationResponse>> update(
      @PathVariable Long id, @RequestBody @Valid ChangeStatusRegistrationRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(registrationService.update(id, request)));
  }

  @Operation(
      summary = "Delete a registration",
      description = "Deletes a registration record permanently by its ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Registration deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Registration not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiWrapper<String>> delete(@PathVariable Long id) {
    registrationService.delete(id);
    return ResponseEntity.ok(ApiWrapper.success("Deleted successfully"));
  }

  @Operation(
      summary = "Get registration by ID",
      description = "Retrieve a registration record by its unique identifier.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Registration retrieved successfully",
        content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
    @ApiResponse(responseCode = "404", description = "Registration not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiWrapper<RegistrationResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiWrapper.success(registrationService.getById(id)));
  }

  @Operation(
      summary = "Get all registrations",
      description =
          "Retrieve a paginated list of all registrations, optionally filtered or sorted.")
  @ApiResponse(
      responseCode = "200",
      description = "List of registrations retrieved successfully",
      content = @Content(schema = @Schema(implementation = RegistrationResponse.class)))
  @GetMapping
  public ResponseEntity<ApiWrapper<ApiPage<RegistrationResponse>>> getAll(Pageable pageable) {
    return ResponseEntity.ok(ApiWrapper.success(ApiPage.of(registrationService.getAll(pageable))));
  }
}
