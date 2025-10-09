package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiPage;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.GradeRequest;
import org.project.createlearnbe.dto.response.GradeResponse;
import org.project.createlearnbe.dto.response.TeacherResponseDto;
import org.project.createlearnbe.serivce.GradeService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Pageable;

@Tag(name = "Grade Management", description = "Endpoints for managing academic grades")
@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @Operation(
            summary = "Get all grades",
            description = "Retrieve a list of all available grades in the system.")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved grade list",
            content = @Content(schema = @Schema(implementation = GradeResponse.class)))
    @GetMapping
    public ResponseEntity<ApiWrapper<ApiPage<GradeResponse>>> getAllGrades(@ParameterObject
                                                                           @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                                           Pageable pageable) {
        return ResponseEntity.ok(ApiWrapper.success(gradeService.getAll(pageable)));
    }

    @Operation(
            summary = "Get grade by ID",
            description = "Retrieve detailed information about a specific grade by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade found",
                    content = @Content(schema = @Schema(implementation = GradeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Grade not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiWrapper<GradeResponse>> getGradeById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiWrapper.success(gradeService.getById(id)));
    }

    @Operation(
            summary = "Create a new grade",
            description = "Create a new grade with optional icon image (sent as multipart/form-data).")
    @ApiResponse(
            responseCode = "200",
            description = "Grade created successfully",
            content = @Content(schema = @Schema(implementation = GradeResponse.class)))
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiWrapper<GradeResponse>> createGrade(@ModelAttribute GradeRequest request) {
        return ResponseEntity.ok(ApiWrapper.success(gradeService.create(request)));
    }

    @Operation(
            summary = "Update existing grade",
            description = "Update the details of an existing grade, including its icon image if provided.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade updated successfully",
                    content = @Content(schema = @Schema(implementation = GradeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Grade not found")
    })
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiWrapper<GradeResponse>> updateGrade(
            @PathVariable Long id, @ModelAttribute GradeRequest request) {
        return ResponseEntity.ok(ApiWrapper.success(gradeService.update(id, request)));
    }

    @Operation(
            summary = "Delete a grade",
            description = "Delete a grade record permanently by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Grade not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiWrapper<String>> deleteGrade(@PathVariable Long id) {
        gradeService.delete(id);
        return ResponseEntity.ok(ApiWrapper.success("Grade deleted successfully"));
    }
}
