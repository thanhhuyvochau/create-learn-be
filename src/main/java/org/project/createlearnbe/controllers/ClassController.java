package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.ClassRequest;
import org.project.createlearnbe.dto.request.GetClassRequest;
import org.project.createlearnbe.dto.response.ClassResponse;
import org.project.createlearnbe.dto.response.GradeResponse;
import org.project.createlearnbe.serivce.ClazzService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Class Management", description = "Endpoints for managing and viewing classes")
@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

  private final ClazzService clazzService;

  // ---------------------- ADMIN ENDPOINTS ----------------------

  @Operation(
      summary = "Get all classes (Admin)",
      description = "Retrieve all classes for administrative purposes.")
  @ApiResponse(
      responseCode = "200",
      description = "Successfully retrieved all classes",
      content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  @GetMapping("/admin")
  public ResponseEntity<ApiWrapper<ApiPage<ClassResponse>>>getAllByAdmin(@ParameterObject
                                                                           @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                                           Pageable pageable) {
    return ResponseEntity.ok(ApiWrapper.success(clazzService.getAll(pageable)));
  }

  @Operation(
      summary = "Get class details by ID (Admin)",
      description = "Retrieve a class by its unique ID.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Class found",
        content = @Content(schema = @Schema(implementation = ApiWrapper.class))),
    @ApiResponse(responseCode = "404", description = "Class not found")
  })
  @GetMapping("/admin/{id}")
  public ResponseEntity<ApiWrapper<ClassResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiWrapper.success(clazzService.getById(id)));
  }

  @Operation(
      summary = "Create a new class",
      description = "Admin can create a new class with assigned subjects, grades, and teacher.")
  @ApiResponse(
      responseCode = "200",
      description = "Class created successfully",
      content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  @PostMapping
  public ResponseEntity<ApiWrapper<ClassResponse>> create(
      @RequestBody(
              description = "Class creation payload",
              required = true,
              content = @Content(schema = @Schema(implementation = ClassRequest.class)))
          @org.springframework.web.bind.annotation.RequestBody
          ClassRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(clazzService.create(request)));
  }

  @Operation(
      summary = "Update class by ID",
      description = "Admin can update class details by providing ID and updated data.")
  @ApiResponse(
      responseCode = "200",
      description = "Class updated successfully",
      content = @Content(schema = @Schema(implementation = ApiWrapper.class)))
  @PutMapping("/{id}")
  public ResponseEntity<ApiWrapper<ClassResponse>> update(
      @PathVariable Long id,
      @RequestBody(
              description = "Class update payload",
              required = true,
              content = @Content(schema = @Schema(implementation = ClassRequest.class)))
          @org.springframework.web.bind.annotation.RequestBody
          ClassRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(clazzService.update(id, request)));
  }

  @Operation(summary = "Delete class by ID", description = "Admin can delete a class by ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Class deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Class not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiWrapper<Void>> delete(@PathVariable Long id) {
    clazzService.delete(id);
    return ResponseEntity.ok(ApiWrapper.success(null));
  }

  // ---------------------- PUBLIC ENDPOINTS ----------------------

  @Operation(
      summary = "Get all public classes",
      description =
          "Retrieve public classes with pagination and optional filters: type (free/popular), gradeId, and subjectId.")
  @ApiResponse(
      responseCode = "200",
      description = "Successfully retrieved public classes",
      content = @Content(schema = @Schema(implementation = ApiPage.class)))
  @GetMapping("/public")
  public ResponseEntity<ApiWrapper<ApiPage<ClassResponse>>> getAllPublicClass(
      @RequestParam(required = false) String type,
      Pageable pageable,
      @RequestParam(required = false) Long gradeId,
      @RequestParam(required = false) Long subjectId) {
    return ResponseEntity.ok(
        ApiWrapper.success(
            clazzService.getAllPublicClass(
                new GetClassRequest(type, pageable, gradeId, subjectId))));
  }

  @Operation(
      summary = "Get public class by ID",
      description = "Retrieve detailed information of a public class by ID.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Class found",
        content = @Content(schema = @Schema(implementation = ApiWrapper.class))),
    @ApiResponse(responseCode = "404", description = "Class not found")
  })
  @GetMapping("/public/{id}")
  public ResponseEntity<ApiWrapper<ClassResponse>> getPublicClassById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiWrapper.success(clazzService.getPublicClassById(id)));
  }
}
