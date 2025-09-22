package org.project.createlearnbe.controllers;


import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<ApiResponse<GradeResponse>> create(@Valid @RequestBody GradeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradeResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(gradeService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeResponse>> update(@PathVariable Long id,
                                                             @Valid @RequestBody GradeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        gradeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Grade deleted successfully"));
    }
}
