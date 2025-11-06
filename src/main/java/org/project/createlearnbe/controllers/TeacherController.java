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
import org.project.createlearnbe.dto.request.TeacherRequestDto;
import org.project.createlearnbe.dto.response.TeacherResponseDto;
import org.project.createlearnbe.serivce.TeacherService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Teacher Management",
    description =
        "APIs for managing teachers including create, update, delete, and retrieve operations.")
@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @Operation(
      summary = "Create a new teacher",
      description =
          "Creates a new teacher profile in the system with basic details such as name, email, and experience.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Teacher created successfully",
        content = @Content(schema = @Schema(implementation = TeacherResponseDto.class))),
    @ApiResponse(responseCode = "400", description = "Invalid teacher data provided")
  })
  @PostMapping
  public ResponseEntity<ApiWrapper<TeacherResponseDto>> createTeacher(
      @Valid @RequestBody TeacherRequestDto dto) {
    return ResponseEntity.ok(ApiWrapper.success(teacherService.createTeacher(dto)));
  }

  @Operation(
      summary = "Get teacher by ID",
      description = "Retrieves detailed information about a specific teacher using their ID.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Teacher retrieved successfully",
        content = @Content(schema = @Schema(implementation = TeacherResponseDto.class))),
    @ApiResponse(responseCode = "404", description = "Teacher not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiWrapper<TeacherResponseDto>> getTeacher(@PathVariable long id) {
    return ResponseEntity.ok(ApiWrapper.success(teacherService.getTeacherById(id)));
  }

  @Operation(
      summary = "Get all teachers",
      description = "Retrieves a list of all teachers available in the system.")
  @ApiResponse(
      responseCode = "200",
      description = "List of teachers retrieved successfully",
      content = @Content(schema = @Schema(implementation = TeacherResponseDto.class)))
  @GetMapping
  public ResponseEntity<ApiWrapper<ApiPage<TeacherResponseDto>>> getAllTeachers(
      @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
          Pageable pageable) {
    return ResponseEntity.ok(ApiWrapper.success(teacherService.getAllTeachers(pageable)));
  }

  @Operation(
      summary = "Update teacher information",
      description =
          "Updates an existing teacher’s profile with new information such as subject, experience, or contact details.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Teacher updated successfully",
        content = @Content(schema = @Schema(implementation = TeacherResponseDto.class))),
    @ApiResponse(responseCode = "404", description = "Teacher not found")
  })
  @PutMapping("/{id}")
  public ResponseEntity<ApiWrapper<TeacherResponseDto>> updateTeacher(
      @PathVariable long id, @Valid @RequestBody TeacherRequestDto dto) {
    return ResponseEntity.ok(ApiWrapper.success(teacherService.updateTeacher(id, dto)));
  }

  @Operation(
      summary = "Delete a teacher",
      description = "Deletes a teacher record from the system using their ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Teacher deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Teacher not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiWrapper<String>> deleteTeacher(@PathVariable long id) {
    teacherService.deleteTeacher(id);
    return ResponseEntity.ok(ApiWrapper.success("Teacher deleted successfully"));
  }
}
