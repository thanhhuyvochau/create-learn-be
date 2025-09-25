package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiResponse;
import org.project.createlearnbe.dto.request.NewsRequest;
import org.project.createlearnbe.dto.response.NewsResponse;
import org.project.createlearnbe.serivce.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
  private final NewsService newsService;

  @GetMapping("/admin")
  public ResponseEntity<ApiResponse<List<NewsResponse>>> getAllNews() {
    return ResponseEntity.ok(ApiResponse.success(newsService.getAllNews()));
  }

  @GetMapping("/admin/{id}")
  public ResponseEntity<ApiResponse<NewsResponse>> getNewsById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(newsService.getNewsById(id)));
  }

  @GetMapping("/public")
  public ResponseEntity<ApiResponse<List<NewsResponse>>> getAllPublicNews() {
    return ResponseEntity.ok(ApiResponse.success(newsService.getAllVisibleNews()));
  }

  @GetMapping("/public/{id}")
  public ResponseEntity<ApiResponse<NewsResponse>> getPublicNewsById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(newsService.getVisibleNewsById(id)));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<NewsResponse>> createNews(@RequestBody NewsRequest request) {
    return ResponseEntity.ok(ApiResponse.success(newsService.createNews(request)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<NewsResponse>> updateNews(
      @PathVariable Long id, @RequestBody NewsRequest request) {
    return ResponseEntity.ok(ApiResponse.success(newsService.updateNews(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<String>> deleteNews(@PathVariable Long id) {
    newsService.deleteNews(id);
    return ResponseEntity.ok(ApiResponse.success("News deleted successfully"));
  }
}
