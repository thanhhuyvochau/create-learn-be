package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.dto.request.ScheduleRequest;
import org.project.createlearnbe.dto.response.ScheduleResponse;
import org.project.createlearnbe.serivce.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Schedule Management",
        description = "APIs for managing class schedules, including creation, updates, retrieval, and deletion."
)
@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @Operation(
          summary = "Create a new schedule",
          description = "Adds a new class schedule (e.g., 'Tuesday - 8am to 9am') linked to a specific class."
  )
  @ApiResponses({
          @ApiResponse(
                  responseCode = "200",
                  description = "Schedule created successfully",
                  content = @Content(schema = @Schema(implementation = ScheduleResponse.class))
          ),
          @ApiResponse(responseCode = "400", description = "Invalid schedule data provided")
  })
  @PostMapping
  public ResponseEntity<ApiWrapper<ScheduleResponse>> create(
          @RequestBody ScheduleRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(scheduleService.create(request)));
  }

  @Operation(
          summary = "Update an existing schedule",
          description = "Updates details of an existing schedule by its ID."
  )
  @ApiResponses({
          @ApiResponse(
                  responseCode = "200",
                  description = "Schedule updated successfully",
                  content = @Content(schema = @Schema(implementation = ScheduleResponse.class))
          ),
          @ApiResponse(responseCode = "404", description = "Schedule not found")
  })
  @PutMapping("/{id}")
  public ResponseEntity<ApiWrapper<ScheduleResponse>> update(
          @PathVariable Long id, @RequestBody ScheduleRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(scheduleService.update(id, request)));
  }

  @Operation(
          summary = "Delete a schedule",
          description = "Deletes a schedule record permanently by its unique ID."
  )
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Schedule deleted successfully"),
          @ApiResponse(responseCode = "404", description = "Schedule not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiWrapper<String>> delete(@PathVariable Long id) {
    scheduleService.delete(id);
    return ResponseEntity.ok(ApiWrapper.success("Deleted successfully"));
  }

  @Operation(
          summary = "Get schedule by ID",
          description = "Retrieves the details of a single schedule by its ID."
  )
  @ApiResponses({
          @ApiResponse(
                  responseCode = "200",
                  description = "Schedule retrieved successfully",
                  content = @Content(schema = @Schema(implementation = ScheduleResponse.class))
          ),
          @ApiResponse(responseCode = "404", description = "Schedule not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiWrapper<ScheduleResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiWrapper.success(scheduleService.getById(id)));
  }

  @Operation(
          summary = "Get all schedules",
          description = "Retrieves a list of all available schedules in the system."
  )
  @ApiResponse(
          responseCode = "200",
          description = "List of schedules retrieved successfully",
          content = @Content(schema = @Schema(implementation = ScheduleResponse.class))
  )
  @GetMapping
  public ResponseEntity<ApiWrapper<List<ScheduleResponse>>> getAll() {
    return ResponseEntity.ok(ApiWrapper.success(scheduleService.getAll()));
  }
}
