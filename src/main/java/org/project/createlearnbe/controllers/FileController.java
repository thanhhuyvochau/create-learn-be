package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.serivce.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

  private final FileStorageService fileStorageService;

  @PostMapping("/upload")
  public ResponseEntity<ApiWrapper<String>> uploadFile(@RequestParam("file") MultipartFile file) {
    String fileUrl = fileStorageService.uploadFile(file);
    return ResponseEntity.ok(ApiWrapper.success(fileUrl));
  }
}
