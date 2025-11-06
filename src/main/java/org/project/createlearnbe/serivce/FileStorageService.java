package org.project.createlearnbe.serivce;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.project.createlearnbe.config.minio.FileStorageException;
import org.project.createlearnbe.config.minio.MinioProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageService {

  private final MinioClient minioClient;
  private final MinioProperties properties;

  public String uploadFile(MultipartFile file) {
    String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();

    try (InputStream is = file.getInputStream()) {
      minioClient.putObject(
          PutObjectArgs.builder().bucket(properties.getBucket()).object(objectName).stream(
                  is, file.getSize(), -1)
              .contentType(file.getContentType())
              .build());
    } catch (Exception e) {
      throw new FileStorageException("Error uploading file", e);
    }

    // Return permanent public URL
    return "/" + properties.getBucket() + "/" + objectName;
  }
}
