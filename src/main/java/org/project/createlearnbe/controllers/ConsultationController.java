package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.dto.request.ConsultationRequest;
import org.project.createlearnbe.dto.response.ConsultationResponse;
import org.project.createlearnbe.serivce.ConsultationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationResponse> create(@RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(consultationService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ConsultationResponse>> getAll() {
        return ResponseEntity.ok(consultationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponse> update(@PathVariable Long id,
                                                       @RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(consultationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
