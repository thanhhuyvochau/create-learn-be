package org.project.createlearnbe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.TeacherRequestDto;
import org.project.createlearnbe.dto.response.TeacherResponseDto;
import org.project.createlearnbe.serivce.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<ApiResponse<TeacherResponseDto>> createTeacher(
            @Valid @RequestBody TeacherRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.createTeacher(dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponseDto>> getTeacher(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getTeacherById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherResponseDto>>> getAllTeachers() {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getAllTeachers()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponseDto>> updateTeacher(
            @PathVariable long id,
            @Valid @RequestBody TeacherRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.updateTeacher(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTeacher(@PathVariable long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok(ApiResponse.success("Teacher deleted successfully"));
    }
}
