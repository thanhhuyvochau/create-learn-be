package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.GradeRequest;
import org.project.createlearnbe.dto.response.GradeResponse;
import org.project.createlearnbe.serivce.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

  private final GradeService gradeService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<GradeResponse>>> getAllGrades() {
    return ResponseEntity.ok(ApiResponse.success(gradeService.getAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<GradeResponse>> getGradeById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(gradeService.getById(id)));
  }

  @PostMapping(consumes = {"multipart/form-data"})
  public ResponseEntity<ApiResponse<GradeResponse>> createGrade(
      @ModelAttribute GradeRequest request) {
    return ResponseEntity.ok(ApiResponse.success(gradeService.create(request)));
  }

  @PutMapping(
      value = "/{id}",
      consumes = {"multipart/form-data"})
  public ResponseEntity<ApiResponse<GradeResponse>> updateGrade(
      @PathVariable Long id, @ModelAttribute GradeRequest request) {
    return ResponseEntity.ok(ApiResponse.success(gradeService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<String>> deleteGrade(@PathVariable Long id) {
    gradeService.delete(id);
    return ResponseEntity.ok(ApiResponse.success("Grade deleted successfully"));
  }
}
