package org.project.createlearnbe.controllers;

import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.exception.types.InvalidFileTypeException;
import org.project.createlearnbe.config.http.ApiWrapper;
import org.project.createlearnbe.serivce.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

  private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = new HashSet<>(
      Arrays.asList("png", "jpg", "jpeg", "gif", "webp")
  );
  private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
  private static final String ALLOWED_EXTENSIONS_STRING = "png, jpg, jpeg, gif, webp";

  private final FileStorageService fileStorageService;

  @PostMapping("/upload")
  public ResponseEntity<ApiWrapper<String>> uploadFile(@RequestParam("file") MultipartFile file) {
    validateFile(file);
    String fileUrl = fileStorageService.uploadImage(file);
    return ResponseEntity.ok(ApiWrapper.success(fileUrl));
  }

  private void validateFile(MultipartFile file) {
    // Check if file is empty
    if (file.isEmpty()) {
      throw new InvalidFileTypeException("File cannot be empty");
    }

    // Check file size
    if (file.getSize() > MAX_FILE_SIZE) {
      throw new InvalidFileTypeException("File size exceeds maximum allowed size of 10MB");
    }

    // Check file extension
    String fileName = file.getOriginalFilename();
    if (fileName == null || fileName.isEmpty()) {
      throw new InvalidFileTypeException("File name is invalid");
    }

    String fileExtension = getFileExtension(fileName).toLowerCase();
    if (!ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension)) {
      throw new InvalidFileTypeException(
          "Invalid file type. File extension '" + fileExtension
              + "' is not allowed. Allowed types: " + ALLOWED_EXTENSIONS_STRING
      );
    }
  }

  private String getFileExtension(String fileName) {
    int lastDotIndex = fileName.lastIndexOf('.');
    if (lastDotIndex > 0) {
      return fileName.substring(lastDotIndex + 1);
    }
    return "";
  }
}
