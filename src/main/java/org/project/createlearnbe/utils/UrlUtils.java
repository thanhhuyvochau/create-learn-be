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
    return String.format("%s/%s", minioProperties.getExternalUrl(), relativePath);
  }

  // This method help strip the external URL from the given path, returning only the relative path.
  // If the external URL is not found in the path, it returns the original path.
  // USE CASE: when set up https for minio, the external URL will be https://domain.com/resource,
  // BUT
  // the path stored in database is /minio/filename, and when update we need to cut the prefix
  // 'resource' and get the original to avoid mistake write to old record when do updating function
  public String stripMinioExternalUrl(String path) {
    String externalUrl = minioProperties.getExternalUrl();
    if (!path.contains(externalUrl)) {
      return path;
    }
    int endIndex = path.indexOf(externalUrl) + externalUrl.length();
    String relativePath = path.substring(endIndex);
    if (!relativePath.startsWith("/")) {
      relativePath = "/" + relativePath;
    }

    return relativePath;
  }
}
