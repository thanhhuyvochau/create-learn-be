package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.SubjectRequest;
import org.project.createlearnbe.dto.response.SubjectResponse;
import org.project.createlearnbe.serivce.SubjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Subject Management",
    description =
        "APIs for managing subjects, including creation, updating, retrieval, and deletion.")
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;

  @Operation(
      summary = "Get all subjects",
      description = "Retrieves a list of all subjects available in the system.")
  @ApiResponse(
      responseCode = "200",
      description = "List of subjects retrieved successfully",
      content = @Content(schema = @Schema(implementation = SubjectResponse.class)))
  @GetMapping
  public ResponseEntity<ApiWrapper<ApiPage<SubjectResponse>>> getAllSubjects(Pageable pageable) {
    return ResponseEntity.ok(ApiWrapper.success(subjectService.getAll(pageable)));
  }

  @Operation(
      summary = "Create a new subject",
      description =
          "Creates a new subject with an uploaded icon image. Accepts multipart/form-data.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Subject created successfully",
        content = @Content(schema = @Schema(implementation = SubjectResponse.class))),
    @ApiResponse(responseCode = "400", description = "Invalid subject data or image upload failed")
  })
  @PostMapping(consumes = {"multipart/form-data"})
  public ResponseEntity<ApiWrapper<SubjectResponse>> createSubject(
      @ModelAttribute SubjectRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(subjectService.create(request)));
  }

  @Operation(
      summary = "Update an existing subject",
      description = "Updates an existing subject by ID, optionally replacing its icon image.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Subject updated successfully",
        content = @Content(schema = @Schema(implementation = SubjectResponse.class))),
    @ApiResponse(responseCode = "404", description = "Subject not found")
  })
  @PutMapping(
      value = "/{id}",
      consumes = {"multipart/form-data"})
  public ResponseEntity<ApiWrapper<SubjectResponse>> updateSubject(
      @PathVariable Long id, @ModelAttribute SubjectRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(subjectService.update(id, request)));
  }

  @Operation(
      summary = "Delete a subject",
      description = "Deletes a subject by its ID. This operation cannot be undone.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Subject deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Subject not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiWrapper<String>> deleteSubject(@PathVariable Long id) {
    subjectService.delete(id);
    return ResponseEntity.ok(ApiWrapper.success("Deleted successfully"));
  }
}
