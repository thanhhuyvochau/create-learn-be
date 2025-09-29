package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.ClassRequest;
import org.project.createlearnbe.dto.response.ClassResponse;
import org.project.createlearnbe.serivce.ClazzService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

  private final ClazzService clazzService;

  @GetMapping("/admin")
  public ResponseEntity<ApiResponse<List<ClassResponse>>> getAllByAdmin() {
    return ResponseEntity.ok(ApiResponse.success(clazzService.getAll()));
  }

  @GetMapping("/admin/{id}")
  public ResponseEntity<ApiResponse<ClassResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(clazzService.getById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<ClassResponse>> create(@RequestBody ClassRequest request) {
    return ResponseEntity.ok(ApiResponse.success(clazzService.create(request)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ClassResponse>> update(
      @PathVariable Long id, @RequestBody ClassRequest request) {
    return ResponseEntity.ok(ApiResponse.success(clazzService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    clazzService.delete(id);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @GetMapping("/public")
  public ResponseEntity<ApiResponse<List<ClassResponse>>> getAllPublicClass() {
    return ResponseEntity.ok(ApiResponse.success(clazzService.getAllPublicClass()));
  }

  @GetMapping("/public/{id}")
  public ResponseEntity<ApiResponse<ClassResponse>> getPublicClassById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(clazzService.getPublicClassById(id)));
  }
}
