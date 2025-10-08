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
import org.project.createlearnbe.dto.request.NewsRequest;
import org.project.createlearnbe.dto.response.NewsResponse;
import org.project.createlearnbe.serivce.NewsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "News Management", description = "Endpoints for managing and retrieving news content")
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

  private final NewsService newsService;

  @Operation(summary = "Get all news (Admin)", description = "Retrieve all news entries, including hidden ones. Requires admin privileges.")
  @ApiResponse(responseCode = "200", description = "List of news retrieved successfully",
          content = @Content(schema = @Schema(implementation = NewsResponse.class)))
  @GetMapping("/admin")
  public ResponseEntity<ApiWrapper<ApiPage<NewsResponse>>> getAllNews(Pageable pageable) {
    return ResponseEntity.ok(ApiWrapper.success(ApiPage.of(newsService.getAllNews(pageable))));
  }

  @Operation(summary = "Get news by ID (Admin)", description = "Retrieve a specific news item by its ID. Admin access required.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "News retrieved successfully",
                  content = @Content(schema = @Schema(implementation = NewsResponse.class))),
          @ApiResponse(responseCode = "404", description = "News not found")
  })
  @GetMapping("/admin/{id}")
  public ResponseEntity<ApiWrapper<NewsResponse>> getNewsById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiWrapper.success(newsService.getNewsById(id)));
  }

  @Operation(summary = "Get all public news", description = "Retrieve all visible (public) news for end users.")
  @ApiResponse(responseCode = "200", description = "Public news list retrieved successfully",
          content = @Content(schema = @Schema(implementation = NewsResponse.class)))
  @GetMapping("/public")
  public ResponseEntity<ApiWrapper<ApiPage<NewsResponse>>> getAllPublicNews(Pageable pageable) {
    return ResponseEntity.ok(ApiWrapper.success(ApiPage.of(newsService.getAllVisibleNews(pageable))));
  }

  @Operation(summary = "Get public news by ID", description = "Retrieve a visible news item by ID.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Public news retrieved successfully",
                  content = @Content(schema = @Schema(implementation = NewsResponse.class))),
          @ApiResponse(responseCode = "404", description = "Public news not found or not visible")
  })
  @GetMapping("/public/{id}")
  public ResponseEntity<ApiWrapper<NewsResponse>> getPublicNewsById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiWrapper.success(newsService.getVisibleNewsById(id)));
  }

  @Operation(summary = "Create a news entry", description = "Create a new news item. Content field supports rich HTML text.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "News created successfully",
                  content = @Content(schema = @Schema(implementation = NewsResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PostMapping
  public ResponseEntity<ApiWrapper<NewsResponse>> createNews(@RequestBody NewsRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(newsService.createNews(request)));
  }

  @Operation(summary = "Update existing news", description = "Update the details of an existing news entry by its ID.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "News updated successfully",
                  content = @Content(schema = @Schema(implementation = NewsResponse.class))),
          @ApiResponse(responseCode = "404", description = "News not found")
  })
  @PutMapping("/{id}")
  public ResponseEntity<ApiWrapper<NewsResponse>> updateNews(
          @PathVariable Long id, @RequestBody NewsRequest request) {
    return ResponseEntity.ok(ApiWrapper.success(newsService.updateNews(id, request)));
  }

  @Operation(summary = "Delete news", description = "Delete a news entry by its ID. Only accessible to admin users.")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "News deleted successfully"),
          @ApiResponse(responseCode = "404", description = "News not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiWrapper<String>> deleteNews(@PathVariable Long id) {
    newsService.deleteNews(id);
    return ResponseEntity.ok(ApiWrapper.success("News deleted successfully"));
  }
}
