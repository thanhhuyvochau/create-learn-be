package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.SubjectRequest;
import org.project.createlearnbe.dto.response.SubjectResponse;
import org.project.createlearnbe.serivce.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<SubjectResponse>>> getAllSubjects() {
    return ResponseEntity.ok(ApiResponse.success(subjectService.getAll()));
  }

  @PostMapping(consumes = {"multipart/form-data"})
  public ResponseEntity<ApiResponse<SubjectResponse>> createSubject(
      @ModelAttribute SubjectRequest request) {
    return ResponseEntity.ok(ApiResponse.success(subjectService.create(request)));
  }

  @PutMapping(
      value = "/{id}",
      consumes = {"multipart/form-data"})
  public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(
      @PathVariable Long id, @ModelAttribute SubjectRequest request) {
    return ResponseEntity.ok(ApiResponse.success(subjectService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<String>> deleteSubject(@PathVariable Long id) {
    subjectService.delete(id);
    return ResponseEntity.ok(ApiResponse.success("Deleted successfully"));
  }
}
