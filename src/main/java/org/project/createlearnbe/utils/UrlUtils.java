package org.project.createlearnbe.utils;

import org.project.createlearnbe.config.minio.MinioProperties;
import org.springframework.stereotype.Component;

@Component
public class UrlUtils {
  private final MinioProperties minioProperties;

  public UrlUtils(MinioProperties minioProperties) {
    this.minioProperties = minioProperties;
  }

  public String buildAbsolutePath(String relativePath) {
    relativePath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
    return String.format("%s/%s", minioProperties.getUrl(), relativePath);
  }
}
