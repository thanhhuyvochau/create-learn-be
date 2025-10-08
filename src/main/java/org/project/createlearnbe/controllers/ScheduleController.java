package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.ScheduleRequest;
import org.project.createlearnbe.dto.response.ScheduleResponse;

import org.project.createlearnbe.serivce.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PostMapping
  public ResponseEntity<ApiResponse<ScheduleResponse>> create(
      @RequestBody ScheduleRequest request) {
    return ResponseEntity.ok(ApiResponse.success(scheduleService.create(request)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ScheduleResponse>> update(
      @PathVariable Long id, @RequestBody ScheduleRequest request) {
    return ResponseEntity.ok(ApiResponse.success(scheduleService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
    scheduleService.delete(id);
    return ResponseEntity.ok(ApiResponse.success("Deleted successfully"));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ScheduleResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(scheduleService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getAll() {
    return ResponseEntity.ok(ApiResponse.success(scheduleService.getAll()));
  }
}
