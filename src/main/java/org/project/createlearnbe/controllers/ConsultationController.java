package org.project.createlearnbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.dto.request.ConsultationRequest;
import org.project.createlearnbe.dto.response.ConsultationResponse;
import org.project.createlearnbe.serivce.ConsultationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Consultation Management", description = "Endpoints for managing user consultations")
@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @Operation(
            summary = "Create a new consultation",
            description = "Submit a consultation request. Typically used by users to contact or schedule discussions."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Consultation created successfully",
            content = @Content(schema = @Schema(implementation = ConsultationResponse.class))
    )
    @PostMapping
    public ResponseEntity<ConsultationResponse> create(
            @RequestBody(description = "Consultation creation payload", required = true,
                    content = @Content(schema = @Schema(implementation = ConsultationRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(consultationService.create(request));
    }

    @Operation(
            summary = "Get all consultations",
            description = "Retrieve a list of all consultation requests. Usually restricted to admin users."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all consultations",
            content = @Content(schema = @Schema(implementation = ConsultationResponse.class))
    )
    @GetMapping
    public ResponseEntity<List<ConsultationResponse>> getAll() {
        return ResponseEntity.ok(consultationService.getAll());
    }

    @Operation(
            summary = "Get consultation by ID",
            description = "Retrieve details of a specific consultation by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consultation found",
                    content = @Content(schema = @Schema(implementation = ConsultationResponse.class))),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.getById(id));
    }

    @Operation(
            summary = "Update a consultation",
            description = "Update details of an existing consultation by ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Consultation updated successfully",
            content = @Content(schema = @Schema(implementation = ConsultationResponse.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponse> update(
            @PathVariable Long id,
            @RequestBody(description = "Consultation update payload", required = true,
                    content = @Content(schema = @Schema(implementation = ConsultationRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(consultationService.update(id, request));
    }

    @Operation(
            summary = "Delete a consultation",
            description = "Delete a consultation record by its ID. Typically restricted to admin users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Consultation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
