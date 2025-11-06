package org.project.createlearnbe.utils;

import java.io.IOException;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {
  public static String toBase64(MultipartFile file) {
    try {
      return Base64.getEncoder().encodeToString(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert image to Base64", e);
    }
  }
}
