package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.JobPostingRequest;
import org.project.createlearnbe.dto.response.JobPostingResponse;
import org.project.createlearnbe.serivce.JobPostingService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Job Postings", description = "Public and admin endpoints for job posting management")
@RestController
@RequestMapping("/api/job-postings")
@RequiredArgsConstructor
public class JobPostingController {

  private final JobPostingService jobPostingService;

  // ── Public endpoints ─────────────────────────────────────────────────────

  @Operation(summary = "Get all active job postings (public)",
      description = "Paginated list of active postings. Optionally filter by department or location.")
  @GetMapping("/public")
  public ResponseEntity<ApiWrapper<ApiPage<JobPostingResponse>>> getPublic(
      @RequestParam(required = false) String department,
      @RequestParam(required = false) String location,
      @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
          Pageable pageable) {
    return ResponseEntity.ok(
        ApiWrapper.success(ApiPage.of(jobPostingService.getPublic(department, location, pageable))));
  }

  @Operation(summary = "Get a single active job posting by id (public)")
  @GetMapping("/public/{id}")
  public ResponseEntity<ApiWrapper<JobPostingResponse>> getPublicById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiWrapper.success(jobPostingService.getById(id)));
  }

  // ── Admin endpoints ───────────────────────────────────────────────────────

  @Operation(summary = "Get all job postings (admin)",
      description = "Returns all non-deleted postings including inactive ones.")
  @GetMapping("/admin")
  public ResponseEntity<ApiWrapper<ApiPage<JobPostingResponse>>> getAllAdmin(
      @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
          Pageable pageable) {
    return ResponseEntity.ok(
        ApiWrapper.success(ApiPage.of(jobPostingService.getAllAdmin(pageable))));
  }

  @Operation(summary = "Create a new job posting")
  @PostMapping
  public ResponseEntity<ApiWrapper<JobPostingResponse>> create(
      @Valid @RequestBody JobPostingRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(jobPostingService.create(request)));
  }

  @Operation(summary = "Update an existing job posting")
  @PutMapping("/{id}")
  public ResponseEntity<ApiWrapper<JobPostingResponse>> update(
      @PathVariable Long id,
      @Valid @RequestBody JobPostingRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(jobPostingService.update(id, request)));
  }

  @Operation(summary = "Delete a job posting (soft delete)")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiWrapper<String>> delete(@PathVariable Long id) {
    jobPostingService.delete(id);
    return ResponseEntity.ok(ApiWrapper.success("Job posting deleted successfully"));
  }
}
